package com.WorkoutTrackerAnalyticsService.repository;

import java.util.List;

public interface WorkoutRepository {
    public List<WorkoutDTO> getAllWorkoutsForUser(Long UserID);
    public WorkoutDTO getLastWorkoutForUser(Long UserID);
    public List<WorkoutDTO> getWorkoutByName(Long userId, String workoutName);

}
