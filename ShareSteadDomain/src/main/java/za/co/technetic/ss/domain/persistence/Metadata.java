package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "metadata")
public class Metadata implements Serializable {

    private static final long serialVersionUID = -5314570958611142942L;

    private Long id;
    private String originalFileName;
    private LocalDate dateCreated;
    private double imgSize;
    private String contentType;
    private Photo photo;

    public Metadata(Long id, String originalFileName, LocalDate dateCreated, double imgSize, String contentType,
                    Photo photo) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.dateCreated = dateCreated;
        this.imgSize = imgSize;
        this.contentType = contentType;
        this.photo = photo;
    }

    public Metadata(Long id, String originalFileName, LocalDate dateCreated, double imgSize, String contentType) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.dateCreated = dateCreated;
        this.imgSize = imgSize;
        this.contentType = contentType;
    }

    public Metadata() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "meta_original_file_name")
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Column(name = "meta_date_created")
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "meta_img_size")
    public double getImgSize() {
        return imgSize;
    }

    public void setImgSize(double imgSize) {
        this.imgSize = imgSize;
    }

    @Column(name = "meta_content_type")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return Double.compare(metadata.imgSize, imgSize) == 0 && id.equals(metadata.id) &&
                originalFileName.equals(metadata.originalFileName) &&
                dateCreated.equals(metadata.dateCreated) && contentType.equals(metadata.contentType) &&
                Objects.equals(photo, metadata.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalFileName, dateCreated, imgSize, contentType, photo);
    }
}
