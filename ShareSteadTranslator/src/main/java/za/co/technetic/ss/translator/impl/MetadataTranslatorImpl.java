package za.co.technetic.ss.translator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.repo.persistence.MetadataRepository;
import za.co.technetic.ss.translator.MetadataTranslator;

@Component
public class MetadataTranslatorImpl implements MetadataTranslator {

    private final MetadataRepository metadataRepository;

    @Autowired
    public MetadataTranslatorImpl(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @Override
    public Metadata saveMetadata(Metadata metadata) {
        return metadataRepository.save(metadata);
    }
}
