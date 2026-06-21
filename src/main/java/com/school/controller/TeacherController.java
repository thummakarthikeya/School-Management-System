package com.school.controller;

import com.school.dto.request.*;
import com.school.dto.response.*;
import com.school.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
public class TeacherController {

    private final TeacherService teacherService;

    // ---------- STUDENTS ----------

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        StudentResponse response = teacherService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created", response));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponse.success("Students fetched",
                teacherService.getAllStudents()));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Student fetched",
                teacherService.getStudentById(id)));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable Long id, @Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Student updated",
                teacherService.updateStudent(id, request)));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        teacherService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted", null));
    }

    // ---------- MARKS ----------

    @PostMapping("/marks")
    public ResponseEntity<ApiResponse<MarkResponse>> addMark(
            @Valid @RequestBody AddMarkRequest request, Authentication authentication) {
        MarkResponse response = teacherService.addMark(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Mark added", response));
    }

    @PutMapping("/marks/{id}")
    public ResponseEntity<ApiResponse<MarkResponse>> updateMark(
            @PathVariable Long id, @Valid @RequestBody AddMarkRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Mark updated",
                teacherService.updateMark(id, request)));
    }

    @GetMapping("/marks/student/{studentId}")
    public ResponseEntity<ApiResponse<List<MarkResponse>>> getMarksByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success("Marks fetched",
                teacherService.getMarksByStudent(studentId)));
    }

    // ---------- ATTENDANCE ----------

    @PostMapping("/attendance")
    public ResponseEntity<ApiResponse<AttendanceResponse>> markAttendance(
            @Valid @RequestBody MarkAttendanceRequest request, Authentication authentication) {
        AttendanceResponse response = teacherService.markAttendance(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attendance marked", response));
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> updateAttendance(
            @PathVariable Long id, @Valid @RequestBody MarkAttendanceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Attendance updated",
                teacherService.updateAttendance(id, request)));
    }

    @GetMapping("/attendance/student/{studentId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success("Attendance fetched",
                teacherService.getAttendanceByStudent(studentId)));
    }

    // ---------- NOTIFICATIONS ----------

    @PostMapping("/notifications")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @Valid @RequestBody CreateNotificationRequest request, Authentication authentication) {
        NotificationResponse response = teacherService.createNotification(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification posted", response));
    }
}