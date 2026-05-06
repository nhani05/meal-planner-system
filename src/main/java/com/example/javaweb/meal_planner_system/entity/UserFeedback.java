package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(length = 20, nullable = false)
    private FeedbackStatus status = FeedbackStatus.PENDING;

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;
}
