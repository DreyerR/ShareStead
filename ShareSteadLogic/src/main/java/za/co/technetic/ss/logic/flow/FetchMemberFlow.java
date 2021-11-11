package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.persistence.Member;

public interface FetchMemberFlow {
    Member fetchMemberByEmail(String email);
}
