package com.school.dto.response;

import com.school.enums.Role;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}