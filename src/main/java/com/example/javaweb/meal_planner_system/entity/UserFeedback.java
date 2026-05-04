package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tblUserFeedback")
@Data
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccount account;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private FeedbackStatus status = FeedbackStatus.PENDING;

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;
}
