package com.school.service;

import com.school.dto.response.*;
import com.school.entity.*;
import com.school.enums.NotificationTarget;
import com.school.enums.Role;
import com.school.exception.ResourceNotFoundException;
import com.school.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final MarkRepository markRepository;
    private final AttendanceRepository attendanceRepository;
    private final NotificationRepository notificationRepository;

    public List<MarkResponse> getMyMarks(String email) {
        Student student = getStudentByEmail(email);
        return markRepository.findByStudentId(student.getId()).stream()
                .map(this::toMarkResponse).toList();
    }

    public List<AttendanceResponse> getMyAttendance(String email) {
        Student student = getStudentByEmail(email);
        return attendanceRepository.findByStudentId(student.getId()).stream()
                .map(this::toAttendanceResponse).toList();
    }

    public List<NotificationResponse> getMyNotifications() {
        return notificationRepository.findByTargetRoleOrAll(NotificationTarget.STUDENT).stream()
                .map(this::toNotificationResponse).toList();
    }

    // ---------- HELPER ----------

    private Student getStudentByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No student profile linked to this account."));
    }

    private MarkResponse toMarkResponse(Mark mark) {
        return MarkResponse.builder()
                .id(mark.getId())
                .studentName(mark.getStudent().getName())
                .rollNumber(mark.getStudent().getRollNumber())
                .subject(mark.getSubject())
                .marksObtained(mark.getMarksObtained())
                .totalMarks(mark.getTotalMarks())
                .examType(mark.getExamType())
                .examDate(mark.getExamDate())
                .addedByTeacher(mark.getAddedBy() != null
                        ? mark.getAddedBy().getUser().getName() : null)
                .build();
    }

    private AttendanceResponse toAttendanceResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .studentName(attendance.getStudent().getName())
                .rollNumber(attendance.getStudent().getRollNumber())
                .date(attendance.getDate())
                .status(attendance.getStatus())
                .markedByTeacher(attendance.getMarkedBy() != null
                        ? attendance.getMarkedBy().getUser().getName() : null)
                .build();
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdBy(notification.getCreatedBy().getName())
                .targetRole(notification.getTargetRole())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}