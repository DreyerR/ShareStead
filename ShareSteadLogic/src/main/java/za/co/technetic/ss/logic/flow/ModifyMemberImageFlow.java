package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.service.GeneralResponse;

public interface ModifyMemberImageFlow {
    GeneralResponse<String> deletePhoto(String email, String keyName);
    GeneralResponse<String> deleteMemberPhotoForAll(String email, String fileName);
}
