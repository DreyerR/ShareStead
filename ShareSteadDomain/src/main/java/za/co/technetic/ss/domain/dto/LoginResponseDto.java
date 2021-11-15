package za.co.technetic.ss.domain.dto;

import java.util.Objects;

public class LoginResponseDto {

    private Long id;
    private String email;
    private String token;

    public LoginResponseDto(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public LoginResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponseDto that = (LoginResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, token);
    }
}
