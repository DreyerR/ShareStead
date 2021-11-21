package za.co.technetic.ss.logic.flow.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.ModifyMetadataFlow;
import za.co.technetic.ss.repo.persistence.MemberPhotoRepository;
import za.co.technetic.ss.repo.persistence.MemberRepository;
import za.co.technetic.ss.repo.persistence.MetadataRepository;
import za.co.technetic.ss.repo.persistence.PhotoRepository;

@Transactional
@Component
public class ModifyMetadataFlowImpl implements ModifyMetadataFlow {

    private final Logger LOGGER = LoggerFactory.getLogger(ModifyMetadataFlowImpl.class);

    private final MetadataRepository metadataTranslator;
    private final MemberPhotoRepository memberPhotoRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public ModifyMetadataFlowImpl(MetadataRepository metadataTranslator, MemberPhotoRepository memberPhotoRepository,
                                  MemberRepository memberRepository, PhotoRepository photoRepository) {
        this.metadataTranslator = metadataTranslator;
        this.memberPhotoRepository = memberPhotoRepository;
        this.memberRepository = memberRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public GeneralResponse<String> updateOriginalFileName(String memberEmail, String photoUrl, String originalFileName) {
        String message = "Successfully updated metadata";
        HttpStatus httpStatus = HttpStatus.OK;

        Member member = memberRepository.findByEmail(memberEmail);
        Photo photo = photoRepository.findPhotoByUrl(photoUrl);

        if (null != member && null != photo) {
            MemberPhoto memberPhoto = memberPhotoRepository.findMemberPhotoByMember_IdAndPhoto_Id(
                    member.getId(),
                    photo.getId()
            );

            if (null != memberPhoto) {
                if (memberPhoto.getOwnerId().equals(member.getId()) || memberPhoto.isModifiable()) {
                    metadataTranslator.updateOriginalFileName(originalFileName, photo.getMetadata().getId());
                }
                else {
                    httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                    message = "You don't have sufficient privileges to update";
                }
            }
            else {
                httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                message = "This photo is not shared with you";
            }
        }
        else {
            LOGGER.warn("Member or photo could not be found. Invalid email or photo name.");
            httpStatus = HttpStatus.NOT_FOUND;
            message = "Something went wrong";
        }

        return new GeneralResponse<>(httpStatus.value(), message);
    }
}
