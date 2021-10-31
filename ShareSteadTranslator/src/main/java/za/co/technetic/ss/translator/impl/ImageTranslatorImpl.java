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
    public void uploadImageToS3(String path, String fileName, Optional<Map<String, Object>> metadata,
                                MultipartFile file) {
        try {
            fileStore.uploadImage(path, fileName, metadata, file.getInputStream());
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
