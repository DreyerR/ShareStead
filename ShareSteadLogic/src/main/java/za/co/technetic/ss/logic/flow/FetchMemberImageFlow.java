package za.co.technetic.ss.logic.flow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface FetchMemberImageFlow {
    ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException;
}
