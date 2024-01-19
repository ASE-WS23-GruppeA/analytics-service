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
    public List<WorkoutProgress> findByUserID(Long userId) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndMuscleGroup(Long userId, String muscleGroup) {
        return null;
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndStartTimeBetween(Long userId, LocalDate startDate, LocalDate endDate) {

        WorkoutProgress workout1 = new WorkoutProgress();
        workout1.setUserId(1L);
        workout1.setExerciseName("Bench-press");
        workout1.setReps(15);
        workout1.setWeight(0.0);
        workout1.setStartTime(LocalDate.of(2023, 1, 1));

        WorkoutProgress workout2 = new WorkoutProgress();
        workout2.setUserId(1L);
        workout2.setExerciseName("Pull-ups");
        workout2.setReps(10);
        workout2.setWeight(0.0);
        workout2.setStartTime(LocalDate.of(2023, 1, 2));


        // Return the mock data
        return List.of(workout1, workout2);
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndExerciseNameAndStartTimeBetween(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        WorkoutProgress workout1 = new WorkoutProgress();
        workout1.setUserId(1L);
        workout1.setExerciseName("Push-ups");
        workout1.setReps(145);
        workout1.setWeight(123.0);
        workout1.setStartTime(LocalDate.of(2023, 1, 1));

        WorkoutProgress workout2 = new WorkoutProgress();
        workout2.setUserId(1L);
        workout2.setExerciseName("Push-ups");
        workout2.setReps(20);
        workout2.setWeight(0.0);
        workout2.setStartTime(LocalDate.of(2023, 1, 2));

        WorkoutProgress workout3 = new WorkoutProgress();
        workout3.setUserId(1L);
        workout3.setExerciseName("Pull-ups");
        workout3.setReps(10);
        workout3.setWeight(0.0);
        workout3.setStartTime(LocalDate.of(2023, 1, 3));

        // Return the mock data based on the provided exerciseName and time range
        return List.of(workout1, workout2, workout3);
    }

    @Override
    public List<WorkoutProgress> findByUserIDAndMuscleGroupAndStartTimeBetween(
            Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
        // Mock data for testing
        WorkoutProgress workout1 = new WorkoutProgress();
        workout1.setUserId(userId);
        workout1.setMuscleGroup(muscleGroup);
        workout1.setExerciseName("Squats");
        workout1.setReps(12);
        workout1.setWeight(50.0);
        workout1.setStartTime(startDate);

        WorkoutProgress workout2 = new WorkoutProgress();
        workout2.setUserId(userId);
        workout2.setMuscleGroup(muscleGroup);
        workout2.setExerciseName("Deadlifts");
        workout2.setReps(8);
        workout2.setWeight(80.0);
        workout2.setStartTime(startDate.plusDays(1));

        // Return the mock data
        return List.of(workout1, workout2);
    }
}
