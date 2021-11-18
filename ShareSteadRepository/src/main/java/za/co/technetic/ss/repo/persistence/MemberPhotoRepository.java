package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.technetic.ss.domain.persistence.MemberPhoto;

import java.util.List;

@Repository
public interface MemberPhotoRepository extends JpaRepository<MemberPhoto, Long> {
    List<MemberPhoto> findAllByMember_Id(Long memberId);
    boolean existsByMember_IdAndPhoto_Id(Long memberId, Long photoId);
    int deleteAllByPhotoId(Long photoId);
    MemberPhoto findMemberPhotoByMember_IdAndPhoto_Id(Long memberId, Long photoId);
    void deleteAllByMember_IdAndPhoto_Id(Long memberId, Long photoId);
}
