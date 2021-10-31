package za.co.technetic.ss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;

import java.util.List;

@RestController
@RequestMapping("image")
public class ImageController {

    private final CreateMemberImageFlow createMemberImageFlow;

    @Autowired
    public ImageController(CreateMemberImageFlow createMemberImageFlow) {
        this.createMemberImageFlow = createMemberImageFlow;
    }

    @PostMapping(
            path = "/{memberId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponse<String>> uploadImage(@PathVariable("memberId") Long memberId,
                                                       @RequestParam("file") MultipartFile file) {
        createMemberImageFlow.uploadImageToS3(memberId, file);

        GeneralResponse<String> generalResponse = new GeneralResponse<>(HttpStatus.OK.value(), "Image " +
                "successfully uploaded", null);

        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
