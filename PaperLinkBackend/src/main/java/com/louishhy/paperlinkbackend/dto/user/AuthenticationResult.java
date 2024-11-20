package com.louishhy.paperlinkbackend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResult {
    private boolean success;
    private String token;
    private String username;
    private Long expiresIn;
}
