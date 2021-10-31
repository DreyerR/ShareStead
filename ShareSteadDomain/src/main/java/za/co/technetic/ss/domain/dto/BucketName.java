package za.co.technetic.ss.domain.dto;

public enum BucketName {

    PROFILE_IMAGE("sharestead-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
