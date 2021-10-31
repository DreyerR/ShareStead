package za.co.technetic.ss.translator;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ImageTranslator {
    void uploadImageToS3(String path, String fileName, Optional<Map<String, Object>> metadata, MultipartFile file);
}
