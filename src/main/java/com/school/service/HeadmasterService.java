package com.school.service;

import com.school.dto.request.CreateTeacherRequest;
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
public class HeadmasterService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- TEACHER CRUD ----------

    public TeacherResponse createTeacher(CreateTeacherRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use.");
        }
        if (teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new BadRequestException("Employee ID already in use.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TEACHER)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        Teacher teacher = Teacher.builder()
                .user(user)
                .employeeId(request.getEmployeeId())
                .subject(request.getSubject())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        return toTeacherResponse(teacherRepository.save(teacher));
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::toTeacherResponse).toList();
    }

    public TeacherResponse getTeacherById(Long id) {
        return toTeacherResponse(findTeacher(id));
    }

    public TeacherResponse updateTeacher(Long id, CreateTeacherRequest request) {
        Teacher teacher = findTeacher(id);
        User user = teacher.getUser();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userRepository.save(user);

        teacher.setSubject(request.getSubject());
        teacher.setPhone(request.getPhone());
        teacher.setAddress(request.getAddress());

        return toTeacherResponse(teacherRepository.save(teacher));
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = findTeacher(id);
        teacherRepository.delete(teacher);
        userRepository.delete(teacher.getUser());
    }

    private Teacher findTeacher(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
    }

    private TeacherResponse toTeacherResponse(Teacher teacher) {
        return TeacherResponse.builder()
                .id(teacher.getId())
                .userId(teacher.getUser().getId())
                .name(teacher.getUser().getName())
                .email(teacher.getUser().getEmail())
                .employeeId(teacher.getEmployeeId())
                .subject(teacher.getSubject())
                .phone(teacher.getPhone())
                .address(teacher.getAddress())
                .build();
    }

    // ---------- VIEW-ONLY: STUDENTS ----------

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toStudentResponse).toList();
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

    // ---------- VIEW-ONLY: MARKS ----------

    public List<MarkResponse> getAllMarks() {
        return markRepository.findAll().stream()
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

    // ---------- VIEW-ONLY: ATTENDANCE ----------

    public List<AttendanceResponse> getAllAttendance() {
        return attendanceRepository.findAll().stream()
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
}