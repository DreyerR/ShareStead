package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    private Long id;
    private String url;
    private Metadata metadata;
    private Set<MemberPhoto> members;

    public Photo(Long id, String url, Metadata metadata, Set<MemberPhoto> members) {
        this.id = id;
        this.url = url;
        this.metadata = metadata;
        this.members = members;
    }

    public Photo(Long id, String url, Metadata metadata) {
        this.id = id;
        this.url = url;
        this.metadata = metadata;
    }

    public Photo(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Photo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "photo_url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToOne(targetEntity = Metadata.class, mappedBy = "photo", fetch = FetchType.LAZY, orphanRemoval = true,
    cascade = CascadeType.ALL)
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @OneToMany(targetEntity = MemberPhoto.class, fetch = FetchType.LAZY, mappedBy = "photo")
    public Set<MemberPhoto> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberPhoto> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id.equals(photo.id) && url.equals(photo.url) && Objects.equals(metadata, photo.metadata) &&
                Objects.equals(members, photo.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, metadata, members);
    }
}
