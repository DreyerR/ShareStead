package za.co.technetic.ss.logic.flow;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateMemberImageFlow {
    void uploadImageToS3(Long memberId, MultipartFile file);
}
