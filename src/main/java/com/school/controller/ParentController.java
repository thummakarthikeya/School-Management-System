package com.school.controller;

import com.school.dto.request.ParentViewRequest;
import com.school.dto.response.ApiResponse;
import com.school.dto.response.ParentViewResponse;
import com.school.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/view")
    public ResponseEntity<ApiResponse<ParentViewResponse>> viewStudentDetails(
            @Valid @RequestBody ParentViewRequest request) {
        ParentViewResponse response = parentService.viewStudentDetails(request);
        return ResponseEntity.ok(ApiResponse.success("Student details fetched", response));
    }
}