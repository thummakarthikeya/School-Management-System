package com.school.dto.response;

import com.school.enums.AttendanceStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class AttendanceResponse {
    private Long id;
    private String studentName;
    private String rollNumber;
    private LocalDate date;
    private AttendanceStatus status;
    private String markedByTeacher;
}