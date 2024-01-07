package com.WorkoutTrackerAnalyticsService.repository;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UserProgressRepository extends JpaRepository<WorkoutProgress, Long> {

    List<WorkoutProgress> findByWorkoutID(Long workoutID);

    List<WorkoutProgress> findByMuscleGroup(String muscleGroup);

    List<WorkoutProgress> findByExerciseID(Long exerciseID);

    List<WorkoutProgress> findByUserID(String userID);


    List<WorkoutProgress> findByWorkoutSetsID(Long workoutSetsID);


    List<WorkoutProgress> findByMuscleGroupAndExerciseName(String muscleGroup, String exerciseName);


    List<WorkoutProgress> findByExerciseNameAndUserID(String exerciseName, String userID);

    List<WorkoutProgress> findByUserIDAndMuscleGroup(String s, String muscleGroup);

    List<WorkoutProgress> findByUserIDAndStartTimeBetween(String s, LocalDateTime startDate, LocalDateTime endDate);
}
