package com.louishhy.paperlinkbackend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String username;
    private Long expiresIn;
}
