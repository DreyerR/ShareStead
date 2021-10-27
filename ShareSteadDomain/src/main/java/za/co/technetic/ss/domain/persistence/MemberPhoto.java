package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "member_photo")
public class MemberPhoto {

    private MemberPhotoKey id;
    private Member member;
    private Photo photo;

    private Long ownerId;
    private boolean isModifiable;

    public MemberPhoto(MemberPhotoKey id, Member member, Photo photo, Long ownerId, boolean isModifiable) {
        this.id = id;
        this.member = member;
        this.photo = photo;
        this.ownerId = ownerId;
        this.isModifiable = isModifiable;
    }

    public MemberPhoto() {
    }

    @EmbeddedId
    public MemberPhotoKey getId() {
        return id;
    }

    public void setId(MemberPhotoKey id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @ManyToOne
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    @Column(name = "owner_id", nullable = false)
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "mp_is_modifiable", columnDefinition = "BIT(1) default 0")
    public boolean isModifiable() {
        return isModifiable;
    }

    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberPhoto that = (MemberPhoto) o;
        return isModifiable == that.isModifiable && id.equals(that.id) &&
                member.equals(that.member) && photo.equals(that.photo) && ownerId.equals(that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, photo, ownerId, isModifiable);
    }
}
