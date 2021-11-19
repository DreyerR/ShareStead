package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "update member_photo mp set mp.mp_is_modifiable = :isModifiable where mp.member_id = :memberId and mp.photo_id = :photoId", nativeQuery = true)
    @Modifying
    void updateIsModifiable(@Param("isModifiable") boolean isModifiable, @Param("memberId") Long memberId, @Param("photoId") Long photoId);
}
