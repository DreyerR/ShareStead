package za.co.technetic.ss.webservice.filestore.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.domain.dto.BucketName;
import za.co.technetic.ss.webservice.filestore.FileStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Component
public class FileStoreImpl implements FileStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStoreImpl.class);
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

    @Override
    public ByteArrayOutputStream downloadImage(Long memberId, String keyName) {
        String path = getPathName(memberId);
        try {
            S3Object s3Object = s3.getObject(new GetObjectRequest(path, keyName));
            InputStream is = s3Object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return outputStream;
        }
        catch (AmazonS3Exception e) {
            LOGGER.error(e.getMessage());
            throw new AmazonS3Exception("The image could not be found");
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePhoto(Long memberId, String keyName) {
        String path = getPathName(memberId);
        try {
            s3.deleteObject(new DeleteObjectRequest(path, keyName));
            return true;
        }
        catch (AmazonServiceException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean copyPhoto(Long sharedById, Long sharedWithId, String fileName) {
        String sharedByPath = getPathName(sharedById);
        String sharedWithPath = getPathName(sharedWithId);
        try {
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                    sharedByPath,
                    fileName,
                    sharedWithPath,
                    fileName
            );

            s3.copyObject(copyObjectRequest);
            return true;
        }
        catch (AmazonServiceException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    private String getPathName(Long memberId) {
        return String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), memberId.toString());
    }

}
