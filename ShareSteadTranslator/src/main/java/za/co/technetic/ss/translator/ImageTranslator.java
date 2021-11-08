package za.co.technetic.ss.translator;

import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.Photo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ImageTranslator {
    void uploadImageToS3(String path, String fileName, Optional<Map<String, Object>> metadata, MultipartFile file);
    ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException;
    Photo savePhoto(Photo photo);
    List<PhotoDto> fetchMemberPhotos(Member member);
    boolean deletePhoto(Long memberId, String keyName);
}
