package org.deodev.model;

import lombok.Data;
import org.deodev.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public User(UserDTO userDto) {
        this.name  = userDto.getName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }
}
