package za.co.technetic.ss.translator;

import za.co.technetic.ss.domain.persistence.MemberPhoto;

public interface MemberPhotoTranslator {
    MemberPhoto saveMemberPhoto(MemberPhoto memberPhoto);
    boolean existsByMemberIdAndPhotoId(Long memberId, Long photoId);
    int deleteAllByPhotoId(Long photoId);
    MemberPhoto findMemberPhotoByMemberIdAndPhotoId(Long memberId, Long photoId);
    void revokeAccess(Long memberId, Long photoId);
}
