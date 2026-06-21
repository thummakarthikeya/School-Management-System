package com.school.dto.response;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TeacherResponse {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String employeeId;
    private String subject;
    private String phone;
    private String address;
}