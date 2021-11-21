package za.co.technetic.ss.translator;

import za.co.technetic.ss.domain.persistence.Metadata;

public interface MetadataTranslator {
    Metadata saveMetadata(Metadata metadata);
    Metadata findByOriginalFileName(String originalFileName);
    void updateOriginalFileName(String originalFileName, Long metaId);
}
