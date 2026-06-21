package com.school.dto.request;

import com.school.enums.NotificationTarget;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNotificationRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String message;
    private NotificationTarget targetRole = NotificationTarget.ALL;
}