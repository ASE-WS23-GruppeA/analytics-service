package com.WorkoutTrackerAnalyticsService.repository;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    List<WorkoutProgress> findByUserIDAndStartTimeBetween(String s, LocalDate startDate, LocalDate endDate);

    @Query("SELECT wp FROM WorkoutProgress wp WHERE wp.userID = :userId " +
            "AND wp.exerciseName = :exerciseName " +
            "AND wp.startTime BETWEEN :startDate AND :endDate")
    List<WorkoutProgress> findByUserIDAndExerciseNameAndStartTimeBetween(
            @Param("userId") String userId,
            @Param("exerciseName") String exerciseName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT wp FROM WorkoutProgress wp " +
            "WHERE wp.userID = :userId " +
            "AND wp.muscleGroup = :muscleGroup " +
            "AND wp.startTime BETWEEN :startDate AND :endDate")
    List<WorkoutProgress> findByUserIDAndMuscleGroupAndStartTimeBetween(
            @Param("userId") String userId,
            @Param("muscleGroup") String muscleGroup,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


}
