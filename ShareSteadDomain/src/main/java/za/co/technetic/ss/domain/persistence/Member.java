package za.co.technetic.ss.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<MemberPhoto> photos;

    public Member(Long id, String firstName, String lastName, String email, String password, Set<MemberPhoto> photos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photos = photos;
    }

    public Member() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "member_firstname", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "member_lastname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "member_email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "member_password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(targetEntity = MemberPhoto.class, fetch = FetchType.LAZY, mappedBy = "member")
    public Set<MemberPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<MemberPhoto> photos) {
        this.photos = photos;
    }
}
