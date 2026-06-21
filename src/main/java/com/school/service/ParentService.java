package com.school.service;

import com.school.dto.request.ParentViewRequest;
import com.school.dto.response.*;
import com.school.entity.*;
import com.school.enums.NotificationTarget;
import com.school.exception.ResourceNotFoundException;
import com.school.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;
    private final AttendanceRepository attendanceRepository;
    private final NotificationRepository notificationRepository;

    public ParentViewResponse viewStudentDetails(ParentViewRequest request) {
        Student student = studentRepository
                .findByRollNumberAndDateOfBirth(request.getRollNumber(), request.getDateOfBirth())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No student found with the given roll number and date of birth."));

        List<MarkResponse> marks = markRepository.findByStudentId(student.getId()).stream()
                .map(this::toMarkResponse).toList();

        List<AttendanceResponse> attendance = attendanceRepository.findByStudentId(student.getId()).stream()
                .map(this::toAttendanceResponse).toList();

        List<NotificationResponse> notifications = notificationRepository
                .findByTargetRoleOrAll(NotificationTarget.PARENT).stream()
                .map(this::toNotificationResponse).toList();

        return ParentViewResponse.builder()
                .student(toStudentResponse(student))
                .marks(marks)
                .attendance(attendance)
                .notifications(notifications)
                .build();
    }

    // ---------- MAPPERS ----------

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .rollNumber(student.getRollNumber())
                .name(student.getName())
                .dateOfBirth(student.getDateOfBirth())
                .className(student.getClassName())
                .section(student.getSection())
                .phone(student.getPhone())
                .parentPhone(student.getParentPhone())
                .address(student.getAddress())
                .classTeacherName(student.getClassTeacher() != null
                        ? student.getClassTeacher().getUser().getName() : null)
                .build();
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