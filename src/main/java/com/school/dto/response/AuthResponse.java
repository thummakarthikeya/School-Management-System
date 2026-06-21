package com.school.dto.response;

import com.school.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private String name;
    private String email;
    private Role role;
}