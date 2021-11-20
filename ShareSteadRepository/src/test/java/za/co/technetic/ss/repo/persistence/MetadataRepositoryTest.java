package za.co.technetic.ss.repo.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.repo.config.RepositoryTestConfig;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
public class MetadataRepositoryTest {

    @Autowired
    MetadataRepository metadataRepository;

    @Test
    public void findByOriginalFileName() {
        Metadata metadata = metadataRepository.findByOriginalFileName("mountain.jpg");
        assertNotNull(metadata);
        assertEquals(metadata.getOriginalFileName(), "mountain.jpg");
        assertEquals(metadata.getContentType(), "image/jpg");
    }

    @Test
    public void findByOriginalFileNameIfNull() {
        Metadata metadata = metadataRepository.findByOriginalFileName("image-not-found.png");
        assertNull(metadata);
    }
}
