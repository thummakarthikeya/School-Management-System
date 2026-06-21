package com.school.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class StudentResponse {
    private Long id;
    private String rollNumber;
    private String name;
    private LocalDate dateOfBirth;
    private String className;
    private String section;
    private String phone;
    private String parentPhone;
    private String address;
    private String classTeacherName;
}