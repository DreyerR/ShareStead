package za.co.technetic.ss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.technetic.ss.domain.dto.MetadataDto;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.FetchMetadataFlow;

@RestController
@RequestMapping("metadata")
public class MetadataController {

    private final FetchMetadataFlow fetchMetadataFlow;

    @Autowired
    public MetadataController(FetchMetadataFlow fetchMetadataFlow) {
        this.fetchMetadataFlow = fetchMetadataFlow;
    }

    @GetMapping("/search/{fileName}")
    public ResponseEntity<GeneralResponse<Metadata>> findImageByFileName(@PathVariable String fileName) {
        MetadataDto metadata = fetchMetadataFlow.findByOriginalFileName(fileName);
        return null;
    }

}
