package org.deodev.dto;

import lombok.Data;

import java.util.InputMismatchException;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;

    public UserDTO(String name, String email, String password) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);

        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void validateName(String name) {
        if (name == null) throw new NullPointerException("User name cannot be null");
        if (name.isEmpty()) throw new InputMismatchException("User name cannot be empty");
    }

    public void validateEmail(String email) {
        if (email == null) throw new NullPointerException("User email cannot be null");
        if (email.isEmpty()) throw new InputMismatchException("User email cannot be empty");
        if (!email.contains("@")) throw new InputMismatchException("Invalid email");
    }

    public void validatePassword(String password) {
        if (password == null) throw new NullPointerException("User password cannot be null");
        if (password.isEmpty()) throw new InputMismatchException("User password cannot be empty");
    }
}
