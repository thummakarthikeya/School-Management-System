package com.school.dto.response;

import com.school.enums.ExamType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class MarkResponse {
    private Long id;
    private String studentName;
    private String rollNumber;
    private String subject;
    private Double marksObtained;
    private Double totalMarks;
    private ExamType examType;
    private LocalDate examDate;
    private String addedByTeacher;
}