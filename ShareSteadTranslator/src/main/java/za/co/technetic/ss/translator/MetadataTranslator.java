package za.co.technetic.ss.translator;

import za.co.technetic.ss.domain.persistence.Metadata;

public interface MetadataTranslator {
    Metadata saveMetadata(Metadata metadata);
}
