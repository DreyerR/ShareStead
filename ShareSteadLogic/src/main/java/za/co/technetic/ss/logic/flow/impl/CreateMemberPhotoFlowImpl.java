package za.co.technetic.ss.logic.flow.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.CreateMemberPhotoFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberPhotoTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

@Transactional
@Component
public class CreateMemberPhotoFlowImpl implements CreateMemberPhotoFlow {

    private final Logger LOGGER = LoggerFactory.getLogger(CreateMemberPhotoFlowImpl.class);

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;
    private final MemberPhotoTranslator memberPhotoTranslator;

    @Autowired
    public CreateMemberPhotoFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator,
                                     MemberPhotoTranslator memberPhotoTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
        this.memberPhotoTranslator = memberPhotoTranslator;
    }

    @Override
    public GeneralResponse<String> sharePhoto(String sharedByEmail, String sharedWithEmail, String fileName, boolean isModifiable) {
        String message = "Successfully shared photo";
        HttpStatus httpStatus = HttpStatus.OK;

        Member sharedBy = memberTranslator.findMemberByEmail(sharedByEmail);
        Member sharedWith = memberTranslator.findMemberByEmail(sharedWithEmail);

        // Check if both members exist
        if (null != sharedBy && null != sharedWith) {
            Photo photo = imageTranslator.findPhotoByUrl(fileName);

            // Check if the photo exists
            if (null != photo) {

                // Check if photo is already shared or not
                if (!memberPhotoTranslator.existsByMemberIdAndPhotoId(sharedWith.getId(), photo.getId())) {

                    // Check if image was shared successfully or not
                    if (!imageTranslator.shareImage(sharedBy.getId(), sharedWith, photo, isModifiable)) {
                        LOGGER.error("Unable to share image. Translator returned false");

                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = "Error: Image could not be shared";
                    }
                }
                else {
                    httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                    message = "Image is already shared with that member";
                }
            }
            else {
                httpStatus = HttpStatus.NOT_FOUND;
                message = "Image could not be found";
            }
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "One or more members could not be found";
        }

        return new GeneralResponse<>(httpStatus.value(), message, null);
    }
}
