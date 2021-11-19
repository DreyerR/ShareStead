package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.dto.MetadataDto;
import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.logic.flow.FetchMetadataFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MetadataTranslator;

@Transactional
@Component
public class FetchMetadataFlowImpl implements FetchMetadataFlow {

    private final MetadataTranslator metadataTranslator;
    private final ImageTranslator imageTranslator;

    @Autowired
    public FetchMetadataFlowImpl(MetadataTranslator metadataTranslator, ImageTranslator imageTranslator) {
        this.metadataTranslator = metadataTranslator;
        this.imageTranslator = imageTranslator;
    }

    @Override
    public MetadataDto findByOriginalFileName(String originalFileName) {
        Metadata metadata = metadataTranslator.findByOriginalFileName(originalFileName);

        if (null != metadata) {
            Photo photo = imageTranslator.findPhotoById(metadata.getPhoto().getId());

            return new MetadataDto(
                    metadata,
                    new PhotoDto(photo, false)
            );
        }

        return null;
    }
}
