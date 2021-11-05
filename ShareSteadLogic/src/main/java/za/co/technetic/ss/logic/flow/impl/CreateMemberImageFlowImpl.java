package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.BucketName;
import za.co.technetic.ss.domain.persistence.*;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberPhotoTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Component
public class CreateMemberImageFlowImpl implements CreateMemberImageFlow {

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;
    private final MemberPhotoTranslator memberPhotoTranslator;

    @Autowired
    public CreateMemberImageFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator,
                                     MemberPhotoTranslator memberPhotoTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
        this.memberPhotoTranslator = memberPhotoTranslator;
    }

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public void uploadImageToS3(Long memberId, MultipartFile file) throws SQLException {

        // Checks if member exists
        Member member = memberTranslator.fetchMemberById(memberId);
        if (null == member) {
            throw new IllegalStateException(String.format("Unable to locate member with ID %d", memberId));
        }

        // Ensuring that the file is a valid type
        ArrayList<String> allowedContentTypes = new ArrayList<>();
        allowedContentTypes.add("png");
        allowedContentTypes.add("jpg");
        allowedContentTypes.add("jpeg");
        allowedContentTypes.add("bmp");
        allowedContentTypes.add("tiff");

        Map<String, Object> metadata = new HashMap<>();
        String[] contentType = Objects.requireNonNull(file.getContentType()).split("/");

        if (!allowedContentTypes.contains(contentType[1])) {
            throw new RuntimeException(String.format("Invalid file type: %s", file.getContentType()));
        }

        if (file.isEmpty()) {
            throw new IllegalStateException("CreateMemberImageFlowImpl: Unable to upload file (no files to upload)");
        }

        // Setting object metadata for AWS
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", file.getSize());

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), member.getId());
        String fileName = String.format("%s-%s.%s", Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0],
                UUID.randomUUID(), contentType[1]);

        imageTranslator.uploadImageToS3(path, fileName, Optional.of(metadata), file);

        // Saving photo reference and metadata in database
        Photo photo = new Photo(
                null,
                fileName,
                new Metadata(
                        null,
                        file.getOriginalFilename(),
                        LocalDate.now(),
                        (double)file.getSize(),
                        file.getContentType()
                ));
        if (null == savePhoto(photo)) {
            throw new SQLException("Unable to save image in database");
        }

        // Saving a photo reference in the composite entity
        MemberPhoto memberPhoto = new MemberPhoto(
                new MemberPhotoKey(member.getId(), photo.getId()),
                member,
                photo,
                member.getId(),
                false
        );

        if (null == saveMemberPhoto(memberPhoto)) {
            throw new SQLException("Unable to create an entry in the composite entity `member_photo`.");
        }
    }

    @Override
    public Photo savePhoto(Photo photo) {
        return imageTranslator.savePhoto(photo);
    }

    @Override
    public MemberPhoto saveMemberPhoto(MemberPhoto memberPhoto) {
        return memberPhotoTranslator.saveMemberPhoto(memberPhoto);
    }
}
