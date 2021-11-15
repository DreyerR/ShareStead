package za.co.technetic.ss.repo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.technetic.ss.domain.persistence.Metadata;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByOriginalFileName(String originalFileName);
}
