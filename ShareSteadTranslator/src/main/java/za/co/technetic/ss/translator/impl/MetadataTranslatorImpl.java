package za.co.technetic.ss.translator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.repo.persistence.MetadataRepository;

@Component
public class MetadataTranslatorImpl implements za.co.technetic.ss.translator.MetadataTranslator {

    private final MetadataRepository metadataRepository;

    @Autowired
    public MetadataTranslatorImpl(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @Override
    public Metadata saveMetadata(Metadata metadata) {
        return metadataRepository.save(metadata);
    }

    @Override
    public Metadata findByOriginalFileName(String originalFileName) {
        return metadataRepository.findByOriginalFileName(originalFileName);
    }

    @Override
    public void updateOriginalFileName(String originalFileName, Long metaId) {
        metadataRepository.updateOriginalFileName(originalFileName, metaId);
    }
}
