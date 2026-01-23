package com.user.users_parking.Dto.Requests;

import com.user.users_parking.Models.TypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLinkVehicle(
        String plate,
        String brand,
        String color,
        String model,
        TypeEnum type,
        String email
) {
}
