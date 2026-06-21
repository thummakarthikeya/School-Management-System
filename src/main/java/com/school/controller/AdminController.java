package com.school.controller;

import com.school.dto.request.*;
import com.school.dto.response.*;
import com.school.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // ---------- USERS ----------

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UserResponse response = adminService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created", response));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched",
                adminService.getAllUsers()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched",
                adminService.getUserById(id)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id, @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User updated",
                adminService.updateUser(id, request)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted", null));
    }

    // ---------- TEACHERS ----------

    @PostMapping("/teachers")
    public ResponseEntity<ApiResponse<TeacherResponse>> createTeacher(
            @Valid @RequestBody CreateTeacherRequest request) {
        TeacherResponse response = adminService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teacher created", response));
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getAllTeachers() {
        return ResponseEntity.ok(ApiResponse.success("Teachers fetched",
                adminService.getAllTeachers()));
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Teacher fetched",
                adminService.getTeacherById(id)));
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> updateTeacher(
            @PathVariable Long id, @Valid @RequestBody CreateTeacherRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Teacher updated",
                adminService.updateTeacher(id, request)));
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTeacher(@PathVariable Long id) {
        adminService.deleteTeacher(id);
        return ResponseEntity.ok(ApiResponse.success("Teacher deleted", null));
    }

    // ---------- STUDENTS ----------

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        StudentResponse response = adminService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created", response));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponse.success("Students fetched",
                adminService.getAllStudents()));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Student fetched",
                adminService.getStudentById(id)));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable Long id, @Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Student updated",
                adminService.updateStudent(id, request)));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        adminService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted", null));
    }
}