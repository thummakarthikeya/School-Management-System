package com.school.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ParentViewRequest {
    @NotBlank
    private String rollNumber;
    @NotNull
    private LocalDate dateOfBirth;
}