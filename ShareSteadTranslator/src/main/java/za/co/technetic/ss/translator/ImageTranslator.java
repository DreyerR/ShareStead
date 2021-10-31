package za.co.technetic.ss.translator;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageTranslator {
    void uploadImageToS3(Long memberId, MultipartFile file);
}
