package za.co.technetic.ss.translator;

import za.co.technetic.ss.domain.dto.MemberDto;

public interface MemberTranslator {
    MemberDto saveMember(MemberDto memberDto);
}
