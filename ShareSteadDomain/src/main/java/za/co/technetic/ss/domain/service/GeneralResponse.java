package za.co.technetic.ss.domain.service;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class GeneralResponse<E> {

    private int code;
    private String message;
    private E payload;

    public GeneralResponse(int code, String message, E payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public GeneralResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public GeneralResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getPayload() {
        return payload;
    }

    public void setPayload(E payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralResponse<?> that = (GeneralResponse<?>) o;
        return code == that.code && Objects.equals(message, that.message) && Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, payload);
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                '}';
    }
}
