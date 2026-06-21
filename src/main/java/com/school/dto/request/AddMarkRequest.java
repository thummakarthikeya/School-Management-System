package com.school.dto.request;

import com.school.enums.ExamType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AddMarkRequest {
    @NotNull
    private Long studentId;
    @NotBlank
    private String subject;
    @NotNull
    private Double marksObtained;
    @NotNull
    private Double totalMarks;
    private ExamType examType;
    private LocalDate examDate;
}