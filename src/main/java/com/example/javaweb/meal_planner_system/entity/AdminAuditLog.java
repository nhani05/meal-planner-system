package com.example.javaweb.meal_planner_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tblAdminAuditLog")
@Data
public class AdminAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private UserAccount admin;

    @Column(nullable = false)
    private String action;

    @Column(name = "target_type")
    private String targetEntity;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "note", columnDefinition = "TEXT")
    private String details;

    @CreationTimestamp
    @Column(name = "acted_at", updatable = false)
    private LocalDateTime performedAt;
}
