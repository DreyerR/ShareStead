package za.co.technetic.ss.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import za.co.technetic.ss.domain.persistence.Member;

public class MemberDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public MemberDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public MemberDto(Member member) {
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

    @JsonIgnore
    public Member getMember() {
        return new Member(
                this.firstName,
                this.lastName,
                this.email,
                this.password
        );
    }

    public MemberDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
