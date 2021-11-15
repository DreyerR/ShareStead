package za.co.technetic.ss.domain.dto;

import za.co.technetic.ss.domain.persistence.Photo;

import java.util.Objects;

public class PhotoDto {

    private Long id;
    private String url;
    private MetadataDto metadata;

    public PhotoDto(Long id, String url, MetadataDto metadata) {
        this.id = id;
        this.url = url;
        this.metadata = metadata;
    }

    public PhotoDto() {
    }

    public PhotoDto(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public PhotoDto(Photo photo) {
        this.id = photo.getId();
        this.url = photo.getUrl();
        if (null != photo.getMetadata()) {
            this.metadata = new MetadataDto(photo.getMetadata(), null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MetadataDto getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDto metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoDto photoDto = (PhotoDto) o;
        return Objects.equals(id, photoDto.id) && Objects.equals(url, photoDto.url) &&
                Objects.equals(metadata, photoDto.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, metadata);
    }
}
