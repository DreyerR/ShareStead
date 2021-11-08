package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.technetic.ss.domain.persistence.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    boolean existsPhotoByUrl(String url);
    void deleteByUrl(String url);
}
