package org.deodev.dto.response;

public class GenericApiResponse<T> {
    String message;
    T data;

    public GenericApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

//    Getters

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

//    Setters

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
