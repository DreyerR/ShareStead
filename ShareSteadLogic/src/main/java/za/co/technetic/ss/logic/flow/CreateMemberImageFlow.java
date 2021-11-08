package za.co.technetic.ss.logic.flow;

import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.domain.persistence.Photo;

import java.sql.SQLException;

public interface CreateMemberImageFlow {
    void uploadImageToS3(Long memberId, MultipartFile file) throws SQLException;
    Photo savePhoto(Photo photo);
    MemberPhoto saveMemberPhoto(MemberPhoto memberPhoto);
    Metadata saveMetadata(Metadata metadata);
}
