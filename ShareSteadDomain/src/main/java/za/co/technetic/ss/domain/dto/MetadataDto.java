package za.co.technetic.ss.domain.dto;

import za.co.technetic.ss.domain.persistence.Metadata;
import za.co.technetic.ss.domain.persistence.Photo;

import java.time.LocalDate;
import java.util.Objects;

public class MetadataDto {

    private Long id;
    private String originalFileName;
    private LocalDate dateCreated;
    private double imgSize;
    private String contentType;

    public MetadataDto(Long id, String originalFileName, LocalDate dateCreated, double imgSize, String contentType) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.dateCreated = dateCreated;
        this.imgSize = imgSize;
        this.contentType = contentType;
    }

    public MetadataDto() {
    }

    public MetadataDto(Metadata metadata) {
        this.id = metadata.getId();
        this.originalFileName = metadata.getOriginalFileName();
        this.dateCreated = metadata.getDateCreated();
        this.imgSize = metadata.getImgSize();
        this.contentType = metadata.getContentType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getImgSize() {
        return imgSize;
    }

    public void setImgSize(double imgSize) {
        this.imgSize = imgSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataDto that = (MetadataDto) o;
        return Double.compare(that.imgSize, imgSize) == 0 && Objects.equals(id, that.id) &&
                Objects.equals(originalFileName, that.originalFileName) &&
                Objects.equals(dateCreated, that.dateCreated) && Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalFileName, dateCreated, imgSize, contentType);
    }
}
