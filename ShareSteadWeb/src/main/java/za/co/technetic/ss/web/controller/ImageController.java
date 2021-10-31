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
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;
import za.co.technetic.ss.logic.flow.FetchMemberImageFlow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("image")
public class ImageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    private final CreateMemberImageFlow createMemberImageFlow;
    private final FetchMemberImageFlow fetchMemberImageFlow;

    @Autowired
    public ImageController(CreateMemberImageFlow createMemberImageFlow, FetchMemberImageFlow fetchMemberImageFlow) {
        this.createMemberImageFlow = createMemberImageFlow;
        this.fetchMemberImageFlow = fetchMemberImageFlow;
    }

    @PostMapping(
            path = "/{memberId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponse<String>> uploadImage(@PathVariable("memberId") Long memberId,
                                                       @RequestParam("file") MultipartFile[] files) {
        Arrays.stream(files).forEach(file -> {
            createMemberImageFlow.uploadImageToS3(memberId, file);
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
