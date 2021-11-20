package za.co.technetic.ss.repo.persistence;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.repo.config.RepositoryTestConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
public class MemberPhotoRepositoryTest {

    @Autowired
    MemberPhotoRepository memberPhotoRepository;

    @After
    public void tearDown() throws Exception {
        memberPhotoRepository = null;
    }

    @Test
    public void findAllByMember_Id() {
        List<MemberPhoto> memberPhotoList = memberPhotoRepository.findAllByMember_Id(1L);
        assertEquals(memberPhotoList.size(), 2);
    }

    @Test
    public void existsByMember_IdAndPhoto_Id() {
        boolean exists = memberPhotoRepository.existsByMember_IdAndPhoto_Id(1L, 2L);
        assertTrue(exists);
    }

    @Test
    public void existsByMember_IdAndPhoto_IdWhenFalse() {
        boolean exists = memberPhotoRepository.existsByMember_IdAndPhoto_Id(2L, 4L);
        assertFalse(exists);
    }

    @Test
    public void findMemberPhotoByMember_IdAndPhoto_Id() {
        MemberPhoto memberPhoto = memberPhotoRepository.findMemberPhotoByMember_IdAndPhoto_Id(2L, 2L);
        assertNotNull(memberPhoto);
        assertEquals(Optional.ofNullable(memberPhoto.getOwnerId()), Optional.of(2L));
    }

    @Test
    public void deleteAllByPhotoId() {
        int total = memberPhotoRepository.deleteAllByPhotoId(2L);
        List<MemberPhoto> memberPhotoList = memberPhotoRepository.findAll();
        assertEquals(memberPhotoList.size(), 1);
    }
}
