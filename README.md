# School-Management-System

You are helping me build a School Management System backend using Java Spring Boot in Eclipse IDE. Here is the complete project specification — follow it exactly and build a production-quality project.

--- PROJECT SPEC ---

PROJECT NAME: school-management-system
TECH STACK: Java 17, Spring Boot 3.x, Spring Security, JWT (jjwt), Spring Data JPA, MySQL, Maven, Lombok, MapStruct

--- ROLES & PERMISSIONS ---

1. ADMIN
   - Full access to everything
   - Can manage all users (create, update, delete, view)
   - Can manage all academic data

2. HEADMASTER
   - CRUD on Teachers (create, update, delete, view teacher profiles)
   - View all students, classes, marks, attendance
   - View all notifications

3. TEACHER
   - CRUD on Students (add, update, delete, view students)
   - Add/update marks for students
   - Mark attendance for students
   - Post notifications to parents/students
   - View their own assigned classes

4. STUDENT
   - Login and view their own marks
   - View their own attendance
   - View notifications

5. PARENT
   - No login required — enters student roll number + date of birth to view:
     - Student marks
     - Student attendance
     - School notifications

--- JWT SECURITY ---
- JWT-based stateless authentication
- Access token: 24h expiry
- Refresh token: 7 days expiry
- Role-based route protection using Spring Security
- Password encrypted with BCrypt

--- DATABASE ENTITIES ---

User (id, name, email, password, role, createdAt)
Teacher (id, user→User, employeeId, subject, phone, address)
Student (id, rollNumber, name, dateOfBirth, class, section, phone, parentPhone, address, teacher→Teacher)
Mark (id, student→Student, subject, marksObtained, totalMarks, examType, examDate, addedBy→Teacher)
Attendance (id, student→Student, date, status[PRESENT/ABSENT/LATE], markedBy→Teacher)
Notification (id, title, message, createdBy→User, targetRole[ALL/STUDENT/PARENT/TEACHER], createdAt)
RefreshToken (id, token, user→User, expiryDate)

--- API ENDPOINTS ---

AUTH:
POST /api/auth/login
POST /api/auth/refresh-token
POST /api/auth/logout

ADMIN:
GET/POST/PUT/DELETE /api/admin/users
GET/POST/PUT/DELETE /api/admin/teachers
GET/POST/PUT/DELETE /api/admin/students

HEADMASTER:
GET/POST/PUT/DELETE /api/headmaster/teachers
GET /api/headmaster/students
GET /api/headmaster/marks
GET /api/headmaster/attendance

TEACHER:
GET/POST/PUT/DELETE /api/teacher/students
POST/PUT /api/teacher/marks
POST/PUT /api/teacher/attendance
POST /api/teacher/notifications

STUDENT:
GET /api/student/marks (own only)
GET /api/student/attendance (own only)
GET /api/student/notifications

PARENT (no auth):
POST /api/parent/view — body: { rollNumber, dateOfBirth }
  → returns marks, attendance, notifications

--- FOLDER STRUCTURE ---

src/main/java/com/school/
├── config/         (SecurityConfig, JwtConfig, CorsConfig)
├── controller/     (AuthController, AdminController, HeadmasterController, TeacherController, StudentController, ParentController)
├── dto/            (request + response DTOs for each entity)
├── entity/         (all JPA entities)
├── enums/          (Role, AttendanceStatus, NotificationTarget, ExamType)
├── exception/      (GlobalExceptionHandler, custom exceptions)
├── repository/     (JPA repositories)
├── security/       (JwtUtil, JwtFilter, UserDetailsServiceImpl)
├── service/        (interfaces + impl for each domain)
└── mapper/         (MapStruct mappers)

--- CODING RULES ---
- Use Lombok (@Data, @Builder, @AllArgsConstructor, @NoArgsConstructor)
- Use DTOs everywhere — never expose entities directly
- Use @PreAuthorize for method-level security
- Return standardized ApiResponse wrapper for all endpoints
- Handle all exceptions in GlobalExceptionHandler
- Use Spring profiles: application-dev.yml and application-prod.yml
- Write clean RESTful APIs with proper HTTP status codes

--- HOW TO CONTINUE ---
I will tell you which phase I have completed. Pick up exactly from where I left off.

PHASES:
Phase 1 — Project setup, pom.xml, folder structure, enums, entities
Phase 2 — Repository layer + DTOs
Phase 3 — JWT security (JwtUtil, JwtFilter, SecurityConfig, UserDetailsServiceImpl)
Phase 4 — Auth service + AuthController
Phase 5 — Admin service + controller
Phase 6 — Headmaster service + controller
Phase 7 — Teacher service + controller
Phase 8 — Student service + controller
Phase 9 — Parent controller (no auth)
Phase 10 — Exception handling, ApiResponse wrapper, final testing

Tell me which phase you are on and I will continue from there.
    
























-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
src/main/java/com/school/
├── enums/
│   ├── Role.java
│   ├── AttendanceStatus.java
│   ├── NotificationTarget.java
│   └── ExamType.java
├── entity/
│   ├── User.java
│   ├── Teacher.java
│   ├── Student.java
│   ├── Mark.java
│   ├── Attendance.java
│   ├── Notification.java
│   └── RefreshToken.java
src/main/resources/
├── application.yml
└── application-dev.yml
pom.xml




-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------

com/school/
├── repository/
│   ├── UserRepository.java
│   ├── TeacherRepository.java
│   ├── StudentRepository.java
│   ├── MarkRepository.java
│   ├── AttendanceRepository.java
│   ├── NotificationRepository.java
│   └── RefreshTokenRepository.java
├── dto/
│   ├── request/
│   │   ├── LoginRequest.java
│   │   ├── RefreshTokenRequest.java
│   │   ├── CreateUserRequest.java
│   │   ├── CreateTeacherRequest.java
│   │   ├── CreateStudentRequest.java
│   │   ├── AddMarkRequest.java
│   │   ├── MarkAttendanceRequest.java
│   │   ├── CreateNotificationRequest.java
│   │   └── ParentViewRequest.java
│   └── response/
│       ├── ApiResponse.java
│       ├── AuthResponse.java
│       ├── UserResponse.java
│       ├── TeacherResponse.java
│       ├── StudentResponse.java
│       ├── MarkResponse.java
│       ├── AttendanceResponse.java
│       ├── NotificationResponse.java
│       └── ParentViewResponse.java



-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------

com/school/
├── config/
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── security/
│   ├── JwtUtil.java
│   ├── JwtAuthFilter.java
│   └── UserDetailsServiceImpl.java
├── service/
│   └── RefreshTokenService.java
├── exception/
│   ├── TokenException.java
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   └── GlobalExceptionHandler.java
