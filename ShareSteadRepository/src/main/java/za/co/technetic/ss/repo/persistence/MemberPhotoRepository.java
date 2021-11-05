package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.domain.persistence.MemberPhotoKey;

@Repository
public interface MemberPhotoRepository extends JpaRepository<MemberPhoto, MemberPhotoKey> {
}
