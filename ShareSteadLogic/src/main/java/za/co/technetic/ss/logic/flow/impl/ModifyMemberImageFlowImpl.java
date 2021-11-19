package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.ModifyMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberPhotoTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

@Transactional
@Component
public class ModifyMemberImageFlowImpl implements ModifyMemberImageFlow {

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;
    private final MemberPhotoTranslator memberPhotoTranslator;

    @Autowired
    public ModifyMemberImageFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator,
                                     MemberPhotoTranslator memberPhotoTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
        this.memberPhotoTranslator = memberPhotoTranslator;
    }

    @Override
    public GeneralResponse<String> deletePhoto(String email, String keyName) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = String.format("Successfully deleted photo '%s'", keyName);
        Member member = memberTranslator.findMemberByEmail(email);

        if (null != member) {
            if (!imageTranslator.deletePhoto(member.getId(), keyName)) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = String.format("Could not delete photo '%s'. See the logs.", keyName);
            }
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = String.format("Member with email '%s' could not be found", email);
        }

        return new GeneralResponse<>(httpStatus.value(), message, null);
    }

    @Override
    public GeneralResponse<String> deleteMemberPhotoForAll(String email, String fileName) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Successfully deleted photo for all members";

        Member member = memberTranslator.findMemberByEmail(email);

        if (null != member) {
            Photo photo = imageTranslator.findPhotoByUrl(fileName);

            if (null != photo) {
                MemberPhoto memberPhoto = memberPhotoTranslator.findMemberPhotoByMemberIdAndPhotoId(member.getId(), photo.getId());
                if (memberPhoto.isModifiable() || member.getId().equals(memberPhoto.getOwnerId())) {
                    if (memberPhotoTranslator.deleteAllByPhotoId(photo.getId()) < 1) {
                        message = "Photo could not be deleted";
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    }
                }
                else {
                    message = "Insufficient access";
                    httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                }
            }
            else {
                message = "Photo could not be found";
                httpStatus = HttpStatus.NOT_FOUND;
            }
        }
        else {
            message = "Member could not be found";
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        }

        return new GeneralResponse<>(httpStatus.value(), message);
    }

    @Override
    public GeneralResponse<String> updateIsModifiable(String email, String filename) {
        String message = "Successfully revoked access";
        HttpStatus httpStatus = HttpStatus.OK;

        Member member = memberTranslator.findMemberByEmail(email);

        if (null != member) {
            Photo photo = imageTranslator.findPhotoByUrl(filename);

            if (null != photo) {
                MemberPhoto memberPhoto = memberPhotoTranslator.findMemberPhotoByMemberIdAndPhotoId(
                        member.getId(),
                        photo.getId()
                );

                if (null != memberPhoto) {
                    if (memberPhoto.isModifiable()) {
                        memberPhotoTranslator.updateIsModifiable(false, member.getId(), photo.getId());
                    }
                    else {
                        message = "This user already does not have access to modify the photo";
                        httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                    }
                }
                else {
                    message = "User does not have access to this photo";
                    httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                }
            }
            else {
                message = "Photo could not be found";
                httpStatus = HttpStatus.NOT_FOUND;
            }
        }
        else {
            message = "User could not be found";
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new GeneralResponse<>(httpStatus.value(), message);
    }
}
