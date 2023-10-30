package com.WorkoutTrackerAnalyticsService.repository;
import com.WorkoutTrackerAnalyticsService.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUserId(Long userId);
}
