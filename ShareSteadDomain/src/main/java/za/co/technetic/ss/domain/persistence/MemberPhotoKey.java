package za.co.technetic.ss.domain.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MemberPhotoKey implements Serializable {

    private Long memberId;
    private Long photoId;

    public MemberPhotoKey(Long memberId, Long photoId) {
        this.memberId = memberId;
        this.photoId = photoId;
    }

    public MemberPhotoKey() {
    }

    @Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "photo_id")
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberPhotoKey that = (MemberPhotoKey) o;
        return memberId.equals(that.memberId) && photoId.equals(that.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, photoId);
    }
}
