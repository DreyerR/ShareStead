package za.co.technetic.ss.webservice.filestore;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStore {

    void uploadImage(String path, String fileName, Optional<Map<String, Object>> optionalMetadata,
                     InputStream inputStream);
}
