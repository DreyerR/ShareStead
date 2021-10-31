package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.BucketName;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

import java.util.*;

@Transactional
@Component
public class CreateMemberImageFlowImpl implements CreateMemberImageFlow {

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;

    @Autowired
    public CreateMemberImageFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
    }

    @Override
    public void uploadImageToS3(Long memberId, MultipartFile file) {

        // Checks if member exists
        Member member = memberTranslator.fetchMemberById(memberId);
        if (null == member) {
            throw new IllegalStateException(String.format("Unable to locate member with ID %d", memberId));
        }

        ArrayList<String> allowedContentTypes = new ArrayList<>();
        allowedContentTypes.add("png");
        allowedContentTypes.add("jpg");
        allowedContentTypes.add("jpeg");
        allowedContentTypes.add("bmp");
        allowedContentTypes.add("tiff");

        Map<String, Object> metadata = new HashMap<>();
        String[] contentType = Objects.requireNonNull(file.getContentType()).split("/");

        if (!allowedContentTypes.contains(contentType[1])) {
            throw new RuntimeException(String.format("Invalid file type %s", file.getContentType()));
        }
        if (file.isEmpty()) {
            throw new IllegalStateException("ImageTranslatorImpl: Unable to upload file (no files to upload)");
        }

        // Setting object metadata
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", file.getSize());

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), member.getId());
        String fileName = String.format("%s-%s.%s", file.getName(), UUID.randomUUID(), contentType[1]);

        imageTranslator.uploadImageToS3(path, fileName, Optional.of(metadata), file);
        metadata.clear();
    }
}
