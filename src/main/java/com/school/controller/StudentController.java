package com.school.controller;

import com.school.dto.response.*;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/marks")
    public ResponseEntity<ApiResponse<List<MarkResponse>>> getMyMarks(
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success("Marks fetched",
                studentService.getMyMarks(authentication.getName())));
    }

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getMyAttendance(
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success("Attendance fetched",
                studentService.getMyAttendance(authentication.getName())));
    }

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMyNotifications() {
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched",
                studentService.getMyNotifications()));
    }
}