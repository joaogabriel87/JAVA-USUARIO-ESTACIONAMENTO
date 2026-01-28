package com.user.users_parking.Dto.Requests;

import com.user.users_parking.Models.Roles;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "O nome é obrigatorio") String name, @NotEmpty(message = "O email é obrigatorio") String email, @NotEmpty(message = "A senha é obrigatoria") String password
) {
}
