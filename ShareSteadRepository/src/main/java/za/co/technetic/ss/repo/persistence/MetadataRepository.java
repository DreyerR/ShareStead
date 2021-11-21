package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.technetic.ss.domain.persistence.Metadata;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByOriginalFileName(String originalFileName);

    @Modifying
    @Query(value = "update metadata m set m.meta_original_file_name = :updatedOriginalFileName where meta_id = :id", nativeQuery = true)
    void updateOriginalFileName(@Param("updatedOriginalFileName") String originalFileName, @Param("id") Long metaId);
}
