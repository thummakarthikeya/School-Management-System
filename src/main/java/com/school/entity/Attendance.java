package com.school.entity;

import com.school.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Attendance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "marked_by")
    private Teacher markedBy;
}