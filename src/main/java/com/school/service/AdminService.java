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
public class AdminService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- USER CRUD ----------

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use.");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();
        return toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        return toUserResponse(findUser(id));
    }

    public UserResponse updateUser(Long id, CreateUserRequest request) {
        User user = findUser(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setRole(request.getRole());
        return toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

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

        // Optional login for student
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
}