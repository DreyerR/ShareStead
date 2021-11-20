package za.co.technetic.ss.repo.persistence;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.repo.config.RepositoryTestConfig;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
public class PhotoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;

    @After
    public void tearDown() throws Exception {
        photoRepository = null;
    }

    @Test
    public void existsPhotoByUrl() {
        boolean exists = photoRepository.existsPhotoByUrl("mountain.jpg");
        assertTrue(exists);
    }

    @Test
    public void existsPhotoByUrlNotFound() {
        boolean exists = photoRepository.existsPhotoByUrl("mount.jpg");
        assertFalse(exists);
    }

    @Test
    public void findPhotoByUrl() {
        Photo photo = photoRepository.findPhotoByUrl("test-img.png");
        assertNotNull(photo);
    }

    @Test
    public void findPhotoByUrlNotFound() {
        Photo photo = photoRepository.findPhotoByUrl("test.png");
        assertNull(photo);
    }

    @Test
    public void findPhotoById() {
        Photo photo = photoRepository.findPhotoById(2L);
        assertNotNull(photo);
        assertEquals(photo.getUrl(), "mountain.jpg");
    }

    @Test
    public void findPhotoByIdNotFound() {
        Photo photo = photoRepository.findPhotoById(4L);
        assertNull(photo);
    }
}
