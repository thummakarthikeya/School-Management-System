package com.school.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;  // null for students without login (optional)

    @Column(unique = true, nullable = false)
    private String rollNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private String className;
    private String section;
    private String phone;
    private String parentPhone;
    private String address;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher classTeacher;
}