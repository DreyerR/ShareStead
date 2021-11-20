package za.co.technetic.ss.repo.persistence;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.repo.config.RepositoryTestConfig;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception {
        memberRepository = null;
    }

    @Test
    public void findByEmail() {
        Member member = memberRepository.findByEmail("rudi@gmail.com");
        assertNotNull(member);
        assertEquals(member.getEmail(), "rudi@gmail.com");
    }
}
