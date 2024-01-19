package com.WorkoutTrackerAnalyticsService.service;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {

    List<WorkoutProgress> getUserProgress(Long workoutId);

    double calculateWorkoutProgress(WorkoutProgress currentWorkout, WorkoutProgress previousWorkout);

    double getTotalVolume(Long userId);

    List<WorkoutProgress> getProgressByMuscleGroup(Long userId, String muscleGroup);

    List<WorkoutProgress> getProgressByDate(Long userId, LocalDate startDate, LocalDate endDate);

    List<WorkoutProgress> getExerciseProgress(Long exerciseId);

    List<WorkoutProgress> getWorkoutProgress(Long workoutId);

    Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getUserTrainingInfo(Long userId, LocalDate startDate, LocalDate endDate);

    Map<String, Double> getAverageWeightProgressByMuscleGroup(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate);

    WorkoutProgress getPreviousWorkout(List<WorkoutProgress> userWorkouts, WorkoutProgress currentWorkout);
}
