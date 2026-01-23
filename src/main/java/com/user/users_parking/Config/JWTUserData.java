package com.user.users_parking.Config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}
