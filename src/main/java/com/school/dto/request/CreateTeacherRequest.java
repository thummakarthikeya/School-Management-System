package com.school.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTeacherRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String employeeId;
    private String subject;
    private String phone;
    private String address;
}