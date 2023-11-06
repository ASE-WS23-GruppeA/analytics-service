package com.WorkoutTrackerAnalyticsService.repository;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface UserProgressRepository extends JpaRepository<WorkoutProgress, Long> {
    List<WorkoutProgress> findByUserId(Long userId);

    List<WorkoutProgress> findByExerciseName(String exerciseName);

    List<WorkoutProgress> findByUserIdAndStartTimeAfter(String userId, LocalDateTime startTime);

    List<WorkoutProgress> findByUserIdAndEndTimeBefore(String userId, LocalDateTime endTime);

    @Query("SELECT w FROM WorkoutProgress w WHERE w.userId = :userId AND w.startTime >= :startDate")
    List<WorkoutProgress> findWorkoutSessionsAfterDate(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate);


}
