package org.deodev.model;

import lombok.Data;
import org.deodev.dto.request.UserRegistrationDTO;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public User(UserRegistrationDTO userRegistrationDto) {
        this.name  = userRegistrationDto.getName();
        this.email = userRegistrationDto.getEmail();
        this.password = userRegistrationDto.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
