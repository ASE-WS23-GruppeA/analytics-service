package com.WorkoutTrackerAnalyticsService.repository;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserProgressRepositoryImpl implements UserProgressRepository{

    @Override
    public List<WorkoutProgress> findByWorkoutID(Long workoutID) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByExerciseID(Long exerciseID) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserID(String userID) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndMuscleGroup(String s, String muscleGroup) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndStartTimeBetween(String s, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndExerciseNameAndStartTimeBetween(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndMuscleGroupAndStartTimeBetween(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
