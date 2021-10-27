package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "metadata")
public class Metadata implements Serializable {

    private Long id;
    private String owner;
    private LocalDate dateCreated;
    private double imgSize;
    private Photo photo;

    public Metadata(Long id, String owner, LocalDate dateCreated, double imgSize, Photo photo) {
        this.id = id;
        this.owner = owner;
        this.dateCreated = dateCreated;
        this.imgSize = imgSize;
        this.photo = photo;
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

    @Column(name = "meta_owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    @OneToOne(targetEntity = Photo.class, mappedBy = "metadata", fetch = FetchType.LAZY)
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
                Objects.equals(owner, metadata.owner) && Objects.equals(dateCreated, metadata.dateCreated) &&
                photo.equals(metadata.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, dateCreated, imgSize, photo);
    }
}
