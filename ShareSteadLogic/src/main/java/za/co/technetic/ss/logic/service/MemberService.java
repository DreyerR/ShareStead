package za.co.technetic.ss.logic.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;

public interface MemberService extends UserDetailsService {
    Member saveMember(MemberDto memberDto);
}
