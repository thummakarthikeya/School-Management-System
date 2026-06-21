package com.school.entity;

import com.school.enums.ExamType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "marks")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Mark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String subject;

    private Double marksObtained;
    private Double totalMarks;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private LocalDate examDate;

    @ManyToOne
    @JoinColumn(name = "added_by")
    private Teacher addedBy;
}