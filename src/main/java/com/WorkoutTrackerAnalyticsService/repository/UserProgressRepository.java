package com.WorkoutTrackerAnalyticsService.repository;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserProgressRepository{

    List<WorkoutProgress> findByWorkoutID(Long workoutID);

    List<WorkoutProgress> findByExerciseID(Long exerciseID);

    List<WorkoutProgress> findByUserID(String userID);

    List<WorkoutProgress> findByUserIDAndMuscleGroup(String s, String muscleGroup);

    List<WorkoutProgress> findByUserIDAndStartTimeBetween(String s, LocalDate startDate, LocalDate endDate);

    List<WorkoutProgress> findByUserIDAndExerciseNameAndStartTimeBetween(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate);

    List<WorkoutProgress> findByUserIDAndMuscleGroupAndStartTimeBetween(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate);


}
