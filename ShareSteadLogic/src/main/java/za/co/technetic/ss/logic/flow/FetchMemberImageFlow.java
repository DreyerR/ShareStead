package za.co.technetic.ss.logic.flow;

import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.service.GeneralResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface FetchMemberImageFlow {
    ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException;
    GeneralResponse<List<PhotoDto>> fetchMemberPhotos(String email);
}
