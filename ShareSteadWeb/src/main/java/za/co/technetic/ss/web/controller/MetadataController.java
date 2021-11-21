package za.co.technetic.ss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.technetic.ss.domain.dto.MetadataDto;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.FetchMetadataFlow;
import za.co.technetic.ss.logic.flow.ModifyMetadataFlow;

@RestController
@RequestMapping("metadata")
@CrossOrigin("*")
public class MetadataController {

    private final FetchMetadataFlow fetchMetadataFlow;
    private final ModifyMetadataFlow modifyMetadataFlow;

    @Autowired
    public MetadataController(FetchMetadataFlow fetchMetadataFlow, ModifyMetadataFlow modifyMetadataFlow) {
        this.fetchMetadataFlow = fetchMetadataFlow;
        this.modifyMetadataFlow = modifyMetadataFlow;
    }

    @GetMapping("/search/{fileName}")
    public ResponseEntity<GeneralResponse<Metadata>> findImageByFileName(@PathVariable String fileName) {
        MetadataDto metadata = fetchMetadataFlow.findByOriginalFileName(fileName);
        return null;
    }

    @PutMapping("/update/{email}/{photoName}/{originalFileName}")
    public ResponseEntity<GeneralResponse<String>> updateOriginalFileName(
            @PathVariable String email,
            @PathVariable String photoName,
            @PathVariable String originalFileName)
    {
        HttpStatus httpStatus = HttpStatus.OK;
        GeneralResponse<String> generalResponse = modifyMetadataFlow.updateOriginalFileName(
                email,
                photoName,
                originalFileName
        );

        if (generalResponse.getCode() == 405)
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        else if (generalResponse.getCode() == 404)
            httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

}
