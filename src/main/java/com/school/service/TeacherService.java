package com.school.service;

import com.school.dto.request.*;
import com.school.dto.response.*;
import com.school.entity.*;
import com.school.enums.Role;
import com.school.exception.BadRequestException;
import com.school.exception.ResourceNotFoundException;
import com.school.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final MarkRepository markRepository;
    private final AttendanceRepository attendanceRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- STUDENT CRUD ----------

    public StudentResponse createStudent(CreateStudentRequest request) {
        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new BadRequestException("Roll number already in use.");
        }

        Student.StudentBuilder builder = Student.builder()
                .rollNumber(request.getRollNumber())
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .className(request.getClassName())
                .section(request.getSection())
                .phone(request.getPhone())
                .parentPhone(request.getParentPhone())
                .address(request.getAddress());

        if (request.getClassTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getClassTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found."));
            builder.classTeacher(teacher);
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already in use.");
            }
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.STUDENT)
                    .enabled(true)
                    .build();
            builder.user(userRepository.save(user));
        }

        return toStudentResponse(studentRepository.save(builder.build()));
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toStudentResponse).toList();
    }

    public StudentResponse getStudentById(Long id) {
        return toStudentResponse(findStudent(id));
    }

    public StudentResponse updateStudent(Long id, CreateStudentRequest request) {
        Student student = findStudent(id);
        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setClassName(request.getClassName());
        student.setSection(request.getSection());
        student.setPhone(request.getPhone());
        student.setParentPhone(request.getParentPhone());
        student.setAddress(request.getAddress());

        if (request.getClassTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getClassTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found."));
            student.setClassTeacher(teacher);
        }

        return toStudentResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        Student student = findStudent(id);
        studentRepository.delete(student);
        if (student.getUser() != null) {
            userRepository.delete(student.getUser());
        }
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

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

    // ---------- MARKS ----------

    public MarkResponse addMark(AddMarkRequest request, String teacherEmail) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        Teacher teacher = getTeacherByEmail(teacherEmail);

        Mark mark = Mark.builder()
                .student(student)
                .subject(request.getSubject())
                .marksObtained(request.getMarksObtained())
                .totalMarks(request.getTotalMarks())
                .examType(request.getExamType())
                .examDate(request.getExamDate())
                .addedBy(teacher)
                .build();

        return toMarkResponse(markRepository.save(mark));
    }

    public MarkResponse updateMark(Long markId, AddMarkRequest request) {
        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new ResourceNotFoundException("Mark record not found."));

        mark.setSubject(request.getSubject());
        mark.setMarksObtained(request.getMarksObtained());
        mark.setTotalMarks(request.getTotalMarks());
        mark.setExamType(request.getExamType());
        mark.setExamDate(request.getExamDate());

        return toMarkResponse(markRepository.save(mark));
    }

    public List<MarkResponse> getMarksByStudent(Long studentId) {
        return markRepository.findByStudentId(studentId).stream()
                .map(this::toMarkResponse).toList();
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

    // ---------- ATTENDANCE ----------

    public AttendanceResponse markAttendance(MarkAttendanceRequest request, String teacherEmail) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        if (attendanceRepository.existsByStudentIdAndDate(student.getId(), request.getDate())) {
            throw new BadRequestException("Attendance already marked for this date.");
        }

        Teacher teacher = getTeacherByEmail(teacherEmail);

        Attendance attendance = Attendance.builder()
                .student(student)
                .date(request.getDate())
                .status(request.getStatus())
                .markedBy(teacher)
                .build();

        return toAttendanceResponse(attendanceRepository.save(attendance));
    }

    public AttendanceResponse updateAttendance(Long attendanceId, MarkAttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found."));

        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());

        return toAttendanceResponse(attendanceRepository.save(attendance));
    }

    public List<AttendanceResponse> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .map(this::toAttendanceResponse).toList();
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

    // ---------- NOTIFICATIONS ----------

    public NotificationResponse createNotification(CreateNotificationRequest request, String teacherEmail) {
        User user = userRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .createdBy(user)
                .targetRole(request.getTargetRole())
                .build();

        return toNotificationResponse(notificationRepository.save(notification));
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

    // ---------- HELPER ----------

    private Teacher getTeacherByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher profile not found for this user."));
    }
}