package com.school.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Teacher {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String employeeId;

    private String subject;
    private String phone;
    private String address;
}