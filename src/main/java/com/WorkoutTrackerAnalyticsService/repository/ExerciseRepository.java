package com.WorkoutTrackerAnalyticsService.repository;

import java.util.List;

public interface ExerciseRepository {
    public List<ExerciseDTO> getAllExercises();
    public ExerciseDTO getExerciseById(Long exerciseId);
}
