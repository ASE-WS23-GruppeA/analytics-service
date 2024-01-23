package com.WorkoutTrackerAnalyticsService.repository;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserProgressRepositoryImpl implements UserProgressRepository{

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public UserProgressRepositoryImpl(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }


    @Override
    public List<WorkoutProgress> findByWorkoutID(Long workoutID) {
        return workoutService.getWorkoutProgress(workoutID);
    }

    @Override
    public List<WorkoutProgress> findByExerciseID(Long exerciseID) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserID(Long userId) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndMuscleGroup(Long userId, String muscleGroup) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndStartTimeBetween(Long userId, LocalDate startDate, LocalDate endDate) {

        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndExerciseNameAndStartTimeBetween(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        return null;
    }

        @Override
        public List<WorkoutProgress> findByUserIDAndMuscleGroupAndStartTimeBetween(
                Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
            return null;
        }
}
