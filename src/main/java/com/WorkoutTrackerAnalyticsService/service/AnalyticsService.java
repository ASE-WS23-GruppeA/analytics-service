package com.WorkoutTrackerAnalyticsService.service;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AnalyticsService {


    Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getUserTrainingInfo(Long userId, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getAverageWeightProgressByMuscleGroup(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate);

}
