package com.school.controller;

import com.school.dto.request.CreateTeacherRequest;
import com.school.dto.response.*;
import com.school.service.HeadmasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/headmaster")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','HEADMASTER')")
public class HeadmasterController {

    private final HeadmasterService headmasterService;

    // ---------- TEACHERS (full CRUD) ----------

    @PostMapping("/teachers")
    public ResponseEntity<ApiResponse<TeacherResponse>> createTeacher(
            @Valid @RequestBody CreateTeacherRequest request) {
        TeacherResponse response = headmasterService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teacher created", response));
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getAllTeachers() {
        return ResponseEntity.ok(ApiResponse.success("Teachers fetched",
                headmasterService.getAllTeachers()));
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Teacher fetched",
                headmasterService.getTeacherById(id)));
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> updateTeacher(
            @PathVariable Long id, @Valid @RequestBody CreateTeacherRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Teacher updated",
                headmasterService.updateTeacher(id, request)));
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTeacher(@PathVariable Long id) {
        headmasterService.deleteTeacher(id);
        return ResponseEntity.ok(ApiResponse.success("Teacher deleted", null));
    }

    // ---------- STUDENTS (view only) ----------

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponse.success("Students fetched",
                headmasterService.getAllStudents()));
    }

    // ---------- MARKS (view only) ----------

    @GetMapping("/marks")
    public ResponseEntity<ApiResponse<List<MarkResponse>>> getAllMarks() {
        return ResponseEntity.ok(ApiResponse.success("Marks fetched",
                headmasterService.getAllMarks()));
    }

    // ---------- ATTENDANCE (view only) ----------

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendance() {
        return ResponseEntity.ok(ApiResponse.success("Attendance fetched",
                headmasterService.getAllAttendance()));
    }
}