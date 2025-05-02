package org.deodev.dto.response;

import lombok.Data;
import org.deodev.model.User;

@Data
public class SignupResponse {
    private final String message = "Signup Successful !";
    private int id;

    public SignupResponse(User user) {
        this.id = user.getId();
    }
}
