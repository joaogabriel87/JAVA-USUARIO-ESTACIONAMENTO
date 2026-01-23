package com.user.users_parking.Dto.Requests;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "O email é obrigatorio") String email, @NotEmpty(message = "A senha é obrigatoria") String password) {
}
