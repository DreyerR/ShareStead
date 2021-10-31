package za.co.technetic.ss.translator.impl;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.BucketName;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.repo.persistence.MemberRepository;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.webservice.filestore.FileStore;

import java.io.IOException;
import java.util.*;

@Component
public class ImageTranslatorImpl implements ImageTranslator {

    private final FileStore fileStore;
    private final MemberRepository memberRepository;

    @Autowired
    public ImageTranslatorImpl(FileStore fileStore, MemberRepository memberRepository) {
        this.fileStore = fileStore;
        this.memberRepository = memberRepository;
    }

    @Override
    public void uploadImageToS3(Long memberId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("ImageTranslatorImpl: Unable to upload file (no files to upload)");
        }

        Member member = memberRepository.findById(memberId).orElse(null);
        if (null == member) {
            throw new IllegalStateException(String.format("Unable to locate member with ID %d", memberId));
        }

//        for (MultipartFile file: files) {
//            if (!Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG, ContentType.IMAGE_TIFF)
//                    .contains(file.getContentType())) {
//                throw new IllegalStateException("File must be an image");
//            }
//        }

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", file.getSize());

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), member.getId());
        String fileName = String.format("%s-%s.PNG", file.getName(), UUID.randomUUID());

        try {
            fileStore.uploadImage(path, fileName, Optional.of(metadata), file.getInputStream());
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
