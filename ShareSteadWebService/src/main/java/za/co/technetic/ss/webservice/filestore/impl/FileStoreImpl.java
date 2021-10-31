package za.co.technetic.ss.webservice.filestore.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.webservice.filestore.FileStore;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Component
public class FileStoreImpl implements FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStoreImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void uploadImage(String path, String fileName, Optional<Map<String, Object>> optionalMetadata,
                            InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                metadata.setContentType(map.get("Content-Type").toString());
                metadata.setContentLength((Long)map.get("Content-Length"));
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, metadata);
        }
        catch (AmazonServiceException e) {
            throw new IllegalStateException("FileStore: Failed to upload image to AWS S3", e);
        }
    }
}
