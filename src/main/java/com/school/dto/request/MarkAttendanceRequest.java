package com.school.dto.request;

import com.school.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MarkAttendanceRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private LocalDate date;
    @NotNull
    private AttendanceStatus status;
}