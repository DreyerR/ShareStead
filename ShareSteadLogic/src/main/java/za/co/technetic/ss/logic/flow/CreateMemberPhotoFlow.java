package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.service.GeneralResponse;

public interface CreateMemberPhotoFlow {
    GeneralResponse<String> sharePhoto(String sharedByEmail, String sharedWithEmail, String fileName, boolean isModifiable);
}
