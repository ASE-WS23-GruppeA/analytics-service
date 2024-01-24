package com.WorkoutTrackerAnalyticsService.repository;

import java.util.List;

import com.WorkoutTrackerAnalyticsService.dto.ExerciseDTO;

public interface ExerciseRepository {

  List<ExerciseDTO> getAllExercises();

  ExerciseDTO getExerciseById(Long exerciseId);

}
