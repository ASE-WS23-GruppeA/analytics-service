package com.WorkoutTrackerAnalyticsService.repository;

import java.util.List;

import com.WorkoutTrackerAnalyticsService.dto.WorkoutDTO;

public interface WorkoutRepository {

  List<WorkoutDTO> getAllWorkoutsForUser(Long UserID);

  WorkoutDTO getLastWorkoutForUser(Long UserID);

  List<WorkoutDTO> getWorkoutByName(Long userId, String workoutName);

}
