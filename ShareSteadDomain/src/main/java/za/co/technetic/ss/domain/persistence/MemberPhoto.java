package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "member_photo")
public class MemberPhoto implements Serializable {

//    private MemberPhotoKey id;
    private Long id;
//    private Long memberId;
//    private Long photoId;
    private Member member;
    private Photo photo;

    private Long ownerId;
    private boolean isModifiable;

    public MemberPhoto(Long id, Member member, Photo photo, Long ownerId, boolean isModifiable) {
        this.id = id;
        this.member = member;
        this.photo = photo;
        this.ownerId = ownerId;
        this.isModifiable = isModifiable;
    }

    public MemberPhoto() {
    }

//    @EmbeddedId
//    public MemberPhotoKey getId() {
//        return id;
//    }

//    public void setId(MemberPhotoKey id) {
//        this.id = id;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_photo_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @Column(name = "member_id")
//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Long memberId) {
//        this.memberId = memberId;
//    }
//
//    @Column(name = "photo_id")
//    public Long getPhotoId() {
//        return photoId;
//    }
//
//    public void setPhotoId(Long photoId) {
//        this.photoId = photoId;
//    }

    @ManyToOne(targetEntity = Member.class)
//    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @ManyToOne(targetEntity = Photo.class)
//    @MapsId("photoId")
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
