package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.UserFeedback;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
    Page<UserFeedback> findByStatus(FeedbackStatus status, Pageable pageable);
    List<UserFeedback> findByAccountId(Long accountId);
}
