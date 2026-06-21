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




http://localhost:8080/api/auth/login // in postman

{
  "email": "admin@school.com",
  "password": "Admin@123"
}

// you will get the access tolken , take that access token 



open new tab and 
http://localhost:8080/api/admin/teachers

 and go to the authorization -> and Barear token -> paste the access token in the right side ->
 open the body json and paste this  -> 
 {
  "name": "Ravi Kumar",
  "email": "ravi.teacher@school.com",
  "password": "Teacher@123",
  "employeeId": "EMP001",
  "subject": "Mathematics",
  "phone": "9876543210",
  "address": "Hyderabad"
}

-> and click on send on the post method -> u will get the status 201.


-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------


com/school/
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   └── HeadmasterController.java
├── service/
│   ├── AuthService.java
│   ├── RefreshTokenService.java
│   ├── AdminService.java
│   └── HeadmasterService.java


POST http://localhost:8080/api/admin/users
Authorization tab → Bearer Token → admin's accessToken
Body → raw → JSON:
{
  "name": "Principal Sharma",
  "email": "headmaster@school.com",
  "password": "Head@123",
  "role": "HEADMASTER"
}


Click Send, and this time carefully check the response panel — confirm it shows 201 Created (or 200 OK) with "success": true and the headmaster's data including "role": "HEADMASTER".


Step 2: Verify it saved:

GET http://localhost:8080/api/admin/users
Authorization tab → Bearer Token → admin's accessToken



Confirm you now see 3 users: admin, teacher (Ravi Kumar), and headmaster (Principal Sharma).
Step 3: Now login as headmaster
POST http://localhost:8080/api/auth/login
Body:
{
  "email": "headmaster@school.com",
  "password": "Head@123"
}



access token

10:29 PM
Now let's run the actual security test from before.

Test 1: Headmaster should be blocked from admin-only routes
GET http://localhost:8080/api/admin/users
Authorization tab → Bearer Token → paste the headmaster's accessToken
Click Send. Expected result: 403 Forbidden.


Test 2: Headmaster should succeed on their own routes
GET http://localhost:8080/api/headmaster/teachers
Authorization tab → Bearer Token → same headmaster accessToken
Click Send. Expected result: 200 OK with the teacher list (Ravi Kumar should show up).




-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------


Test it
You already have a teacher account (ravi.teacher@school.com / Teacher@123). Login as that teacher first to get a token, then try:
1. Create a student:
POST http://localhost:8080/api/teacher/students
Authorization: Bearer <teacher-access-token>

{
  "rollNumber": "S001",
  "name": "Anjali Verma",
  "dateOfBirth": "2012-05-14",
  "className": "8",
  "section": "A",
  "phone": "9000000001",
  "parentPhone": "9000000002",
  "address": "Hyderabad"
}
2. Add a mark for that student (use the id returned above, not the roll number):
POST http://localhost:8080/api/teacher/marks
Authorization: Bearer <teacher-access-token>

{
  "studentId": 1,
  "subject": "Mathematics",
  "marksObtained": 85,
  "totalMarks": 100,
  "examType": "MID_TERM",
  "examDate": "2026-06-15"
}
3. Mark attendance:
POST http://localhost:8080/api/teacher/attendance
Authorization: Bearer <teacher-access-token>

{
  "studentId": 1,
  "date": "2026-06-21",
  "status": "PRESENT"
}
4. Post a notification:
POST http://localhost:8080/api/teacher/notifications
Authorization: Bearer <teacher-access-token>

{
  "title": "Holiday Notice",
  "message": "School will remain closed on Friday for maintenance.",
  "targetRole": "ALL"
}
Try all four and let me know the results.

Your structure now:
com/school/
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   ├── HeadmasterController.java
│   └── TeacherController.java
├── service/
│   ├── AuthService.java
│   ├── RefreshTokenService.java
│   ├── AdminService.java
│   ├── HeadmasterService.java
│   └── TeacherService.java
Say "continue Phase 8" when ready, and I'll build the Student service + controller.



-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------

Why no studentId in the URL or body?
This is the key security design here: a student should never be able to pass studentId=5 and see someone else's marks. Instead, we always derive the student record from authentication.getName() — which comes from the verified JWT email — so a student can only ever see their own data, no matter what they try to send.

Test it
The student you created earlier (Anjali Verma, roll S001) doesn't have login credentials yet, because we didn't pass email/password when creating her. Let's create a student with login this time so we can test this properly.
1. Create a new student with login credentials (as teacher):
POST http://localhost:8080/api/teacher/students
Authorization: Bearer <teacher-access-token>

{
  "rollNumber": "S002",
  "name": "Karthik Reddy",
  "dateOfBirth": "2011-08-20",
  "className": "9",
  "section": "B",
  "phone": "9000000003",
  "parentPhone": "9000000004",
  "address": "Hyderabad",
  "email": "karthik.student@school.com",
  "password": "Student@123"
}
2. Add a mark and mark attendance for this new student (use the new id returned, e.g. 2):
POST http://localhost:8080/api/teacher/marks
Authorization: Bearer <teacher-access-token>

{
  "studentId": 2,
  "subject": "Science",
  "marksObtained": 78,
  "totalMarks": 100,
  "examType": "UNIT_TEST",
  "examDate": "2026-06-18"
}
POST http://localhost:8080/api/teacher/attendance
Authorization: Bearer <teacher-access-token>

{
  "studentId": 2,
  "date": "2026-06-21",
  "status": "PRESENT"
}
3. Login as the student:
POST http://localhost:8080/api/auth/login

{
  "email": "karthik.student@school.com",
  "password": "Student@123"
}
Copy the accessToken.
4. View own marks:
GET http://localhost:8080/api/student/marks
Authorization: Bearer <student-access-token>
Should return only Karthik's Science mark.
5. View own attendance:
GET http://localhost:8080/api/student/attendance
Authorization: Bearer <student-access-token>
6. View notifications:
GET http://localhost:8080/api/student/notifications
Authorization: Bearer <student-access-token>
Should return the "Holiday Notice" you posted earlier (since it was targeted to ALL).

Your structure now:
com/school/
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   ├── HeadmasterController.java
│   ├── TeacherController.java
│   └── StudentController.java
├── service/
│   ├── AuthService.java
│   ├── RefreshTokenService.java
│   ├── AdminService.java
│   ├── HeadmasterService.java
│   ├── TeacherService.java
│   └── StudentService.java
Try these tests and let me know the results. Say "continue Phase 9" when ready, and I'll build the Parent controller — the no-login lookup by roll number + date of birth.




-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------



Why this is safe even without login
A parent needs both the exact roll number and the exact date of birth to get any data back — this acts as a shared secret only the family would know (it's commonly printed on report cards / admission forms), so it's a reasonable lightweight verification for a read-only, non-sensitive-action endpoint. If you ever wanted to harden this further later (e.g. rate-limiting to stop someone brute-forcing roll numbers), that would be a good addition — but it's not required for the assignment scope you described.

Test it
No token needed at all for this one.
POST http://localhost:8080/api/parent/view
Content-Type: application/json

{
  "rollNumber": "S002",
  "dateOfBirth": "2011-08-20"
}
Expected response: Karthik Reddy's full profile, his Science mark, his attendance record, and any notifications targeted to PARENT or ALL.
Also test the failure case — wrong date of birth:
json{
  "rollNumber": "S002",
  "dateOfBirth": "2011-08-21"
}
Should return 404 Not Found with "No student found with the given roll number and date of birth."

Your structure now:
com/school/
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   ├── HeadmasterController.java
│   ├── TeacherController.java
│   ├── StudentController.java
│   └── ParentController.java
├── service/
│   ├── AuthService.java
│   ├── RefreshTokenService.java
│   ├── AdminService.java
│   ├── HeadmasterService.java
│   ├── TeacherService.java
│   ├── StudentService.java
│   └── ParentService.java
Try it and let me know the results. Say "continue Phase 10" when ready — that's the final phase: polishing exception handling, double-checking the ApiResponse wrapper everywhere, and a full end-to-end test checklist to make sure every role boundary is airtight.



-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------



