package za.co.technetic.ss.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;
import za.co.technetic.ss.logic.flow.CreateMemberPhotoFlow;
import za.co.technetic.ss.logic.flow.FetchMemberImageFlow;
import za.co.technetic.ss.logic.flow.ModifyMemberImageFlow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("image")
@CrossOrigin("*")
public class ImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final CreateMemberImageFlow createMemberImageFlow;
    private final FetchMemberImageFlow fetchMemberImageFlow;
    private final ModifyMemberImageFlow modifyMemberImageFlow;
    private final CreateMemberPhotoFlow createMemberPhotoFlow;

    @Autowired
    public ImageController(CreateMemberImageFlow createMemberImageFlow, FetchMemberImageFlow fetchMemberImageFlow,
                           ModifyMemberImageFlow modifyMemberImageFlow, CreateMemberPhotoFlow createMemberPhotoFlow) {
        this.createMemberImageFlow = createMemberImageFlow;
        this.fetchMemberImageFlow = fetchMemberImageFlow;
        this.modifyMemberImageFlow = modifyMemberImageFlow;
        this.createMemberPhotoFlow = createMemberPhotoFlow;
    }

    @PostMapping(
            path = "/{memberId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponse<String>> uploadImage(@PathVariable("memberId") Long memberId,
                                                       @RequestParam("file") MultipartFile[] files) {

        Arrays.stream(files).forEach(file -> {
            try {
                createMemberImageFlow.uploadImageToS3(memberId, file);
            } catch (SQLException e) {
                LOGGER.error("Unable to save photo in database: {}", e.getMessage());
            }
        });

        String message = String.format("Successfully uploaded %d file(s)", files.length);
        GeneralResponse<String> generalResponse = new GeneralResponse<>(HttpStatus.OK.value(), message, null);

        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{memberId}/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long memberId, @PathVariable String fileName) throws IOException {
        ByteArrayOutputStream downloadInputStream = fetchMemberImageFlow.downloadImage(memberId, fileName);
        return ResponseEntity.ok()
                .contentType(contentType(fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(downloadInputStream.toByteArray());
    }

    @GetMapping("/{memberId}/display/{fileName}")
    public ResponseEntity<byte[]> displayImage(@PathVariable Long memberId, @PathVariable String fileName) throws IOException {
        ByteArrayOutputStream downloadInputStream = fetchMemberImageFlow.downloadImage(memberId, fileName);
        return ResponseEntity.ok()
                .contentType(contentType(fileName))
                .body(downloadInputStream.toByteArray());
    }

    @GetMapping("/fetch/{email}")
    public ResponseEntity<GeneralResponse<List<PhotoDto>>> fetchMemberPhotos(@PathVariable String email) {
        HttpStatus httpStatus = HttpStatus.OK;

        GeneralResponse<List<PhotoDto>> generalResponse = fetchMemberImageFlow.fetchMemberPhotos(email);

        if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    @DeleteMapping("/delete/{email}/{fileName}")
    public ResponseEntity<GeneralResponse<String>> deleteMemberPhotoPerMember(
            @PathVariable String email,
            @PathVariable String fileName
    ) {
        HttpStatus httpStatus = HttpStatus.OK;
        GeneralResponse<String> generalResponse = modifyMemberImageFlow.deletePhoto(email, fileName);

        if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    @DeleteMapping("/delete/all/{deletedBy}/{fileName}")
    public ResponseEntity<GeneralResponse<String>> deleteMemberPhotoForAll(
            @PathVariable String deletedBy,
            @PathVariable String fileName
    ) {
        GeneralResponse<String> generalResponse = modifyMemberImageFlow.deleteMemberPhotoForAll(deletedBy, fileName);

        HttpStatus httpStatus = HttpStatus.OK;
        if (generalResponse.getCode() == 405)
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        else if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;
        else if (generalResponse.getCode() == 500)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    @PostMapping("/share/{sharedBy}/{sharedWith}/{fileName}")
    public ResponseEntity<GeneralResponse<String>> sharePhoto(
        @PathVariable String sharedBy,
        @PathVariable String sharedWith,
        @PathVariable String fileName,
        @RequestParam(value = "isModifiable", required = false) boolean isModifiable
    ) {
        GeneralResponse<String> generalResponse = createMemberPhotoFlow.sharePhoto(
                sharedBy,
                sharedWith,
                fileName,
                isModifiable
        );

        HttpStatus httpStatus = HttpStatus.OK;
        if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;
        else if (generalResponse.getCode() == 405)
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        else if (generalResponse.getCode() == 500)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    @PutMapping("/revoke/{emailToRevoke}/{fileName}")
    public ResponseEntity<GeneralResponse<String>> revokeMemberAccessToPhoto(@PathVariable String emailToRevoke,
                                                                             @PathVariable String fileName)
    {
        GeneralResponse<String> generalResponse = modifyMemberImageFlow.revokeAccess(emailToRevoke, fileName);
        HttpStatus httpStatus = HttpStatus.OK;

        if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;
        else if (generalResponse.getCode() == 405)
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    private MediaType contentType(String fileName) {
        String[] fileArrSplit = fileName.split("\\.");
        String fileExtension  = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
