package za.co.technetic.ss.translator;

import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;

public interface MemberTranslator {
    Member saveMember(MemberDto memberDto);
    Member fetchMemberById(Long id);
    Member findMemberByEmail(String email);
}
