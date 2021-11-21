package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.service.GeneralResponse;

public interface ModifyMetadataFlow {
    GeneralResponse<String> updateOriginalFileName(String memberEmail, String photoUrl, String originalFileName);
}
