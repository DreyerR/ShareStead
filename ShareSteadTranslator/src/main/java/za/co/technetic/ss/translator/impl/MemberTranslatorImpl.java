package za.co.technetic.ss.translator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.repo.persistence.MemberRepository;
import za.co.technetic.ss.translator.MemberTranslator;

@Component
public class MemberTranslatorImpl implements MemberTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberTranslatorImpl.class);
    private final MemberRepository memberRepository;

    @Autowired
    public MemberTranslatorImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member saveMember(MemberDto memberDto) {
        try {
            return memberRepository.save(memberDto.getMember());
        }
        catch (Exception e) {
            LOGGER.error("Unable to save member: {}", e.getMessage());
            throw new RuntimeException("Unable to register member");
        }
    }

    @Override
    public Member fetchMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
