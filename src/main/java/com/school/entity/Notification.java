package com.school.entity;

import com.school.enums.NotificationTarget;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    private NotificationTarget targetRole;

    @CreationTimestamp
    private LocalDateTime createdAt;
}