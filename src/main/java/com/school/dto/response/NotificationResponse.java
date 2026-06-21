package com.school.dto.response;

import com.school.enums.NotificationTarget;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private String createdBy;
    private NotificationTarget targetRole;
    private LocalDateTime createdAt;
}