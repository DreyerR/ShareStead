package za.co.technetic.ss.webservice.filestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStore {

    void uploadImage(String path, String fileName, Optional<Map<String, Object>> optionalMetadata,
                     InputStream inputStream);

    ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException;
    boolean deletePhoto(Long memberId, String keyName);
}
