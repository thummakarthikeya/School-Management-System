package com.school.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateStudentRequest {
    @NotBlank
    private String rollNumber;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate dateOfBirth;
    private String className;
    private String section;
    private String phone;
    private String parentPhone;
    private String address;
    private Long classTeacherId;
    private String email;
    private String password;
}