package za.co.technetic.ss.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.logic.service.MemberService;
import za.co.technetic.ss.translator.MemberTranslator;

import java.util.ArrayList;

@Transactional
@Component
public class MemberServiceImpl implements MemberService {

    private final MemberTranslator memberTranslator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberServiceImpl(MemberTranslator memberTranslator, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberTranslator = memberTranslator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Member saveMember(MemberDto memberDto) {
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        return memberTranslator.saveMember(memberDto);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberTranslator.findMemberByEmail(email);

        if (null == member) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }
}
