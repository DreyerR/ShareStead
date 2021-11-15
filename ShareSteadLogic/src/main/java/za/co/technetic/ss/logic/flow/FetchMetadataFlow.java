package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.dto.MetadataDto;

public interface FetchMetadataFlow {
    MetadataDto findByOriginalFileName(String originalFileName);
}
