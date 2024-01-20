package com.WorkoutTrackerAnalyticsService.service;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final UserProgressRepositoryImpl userProgressRepositoryImpl;


    public AnalyticsServiceImpl(UserProgressRepositoryImpl userProgressRepositoryImpl) {
        this.userProgressRepositoryImpl = userProgressRepositoryImpl;
    }

    public List<WorkoutProgress> getUserProgress(Long workoutId) {
        return userProgressRepositoryImpl.findByWorkoutID(workoutId);
    }


    public double calculateWorkoutProgress(WorkoutProgress currentWorkout, WorkoutProgress previousWorkout) {
        // Calculate overall progress for each workout session (in kg)
        double progress = 0.0;
        if (currentWorkout != null && previousWorkout != null) {
            progress = currentWorkout.getWeight() - previousWorkout.getWeight();
        }
        return progress;
    }


    public double getTotalVolume(Long userId) {
        List<WorkoutProgress> userProgress = userProgressRepositoryImpl.findByUserID(userId);
        return userProgress.stream().mapToDouble(WorkoutProgress::getWeight).sum();
    }

    public List<WorkoutProgress> getProgressByMuscleGroup(Long userId, String muscleGroup) {
        return userProgressRepositoryImpl.findByUserIDAndMuscleGroup(userId, muscleGroup);
    }

    public List<WorkoutProgress> getProgressByDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return userProgressRepositoryImpl.findByUserIDAndStartTimeBetween(userId, startDate, endDate);
    }

    public List<WorkoutProgress> getExerciseProgress(Long exerciseId) {
        return userProgressRepositoryImpl.findByExerciseID(exerciseId);
    }

    public List<WorkoutProgress> getWorkoutProgress(Long workoutId) {
        return userProgressRepositoryImpl.findByWorkoutID(workoutId);
    }

    public Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepositoryImpl.findByUserIDAndExerciseNameAndStartTimeBetween(
                userId, exerciseName, startDate, endDate);


        Map<String, Double> weightProgressMap = new LinkedHashMap<>();

        for (WorkoutProgress workout : userWorkouts) {
            // Check if the workout date is within the specified range
            if (exerciseName.equals(workout.getExerciseName()) && userId.equals(workout.getUserId()) &&
                    workout.getStartTime() != null &&
                    (workout.getStartTime().isEqual(startDate) || workout.getStartTime().isAfter(startDate)) &&
                    (workout.getStartTime().isEqual(endDate) || workout.getStartTime().isBefore(endDate))) {
                double weight = workout.getWeight();
                weightProgressMap.put(workout.getStartTime().toString(), weight);
            }

        }
        return weightProgressMap;
    }

    public Map<String, Object> getUserTrainingInfo(Long userId, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepositoryImpl.findByUserIDAndStartTimeBetween(
                userId, startDate, endDate);

        Map<LocalDate, List<String>> trainingInfoMap = new HashMap<>();

        for (WorkoutProgress workout : userWorkouts) {
            if (userId.equals(workout.getUserId()) &&
                    workout.getStartTime() != null &&
                    (workout.getStartTime().isEqual(startDate) || workout.getStartTime().isAfter(startDate)) &&
                    (workout.getStartTime().isEqual(endDate) || workout.getStartTime().isBefore(endDate))) {
                LocalDate workoutDate = workout.getStartTime();
                String trainingInfo = workout.getExerciseName() + " - Sets: " + workout.getWorkoutSetsID() + ", Reps: " + workout.getReps();

                trainingInfoMap.computeIfAbsent(workoutDate, k -> new ArrayList<>()).add(trainingInfo);
            }
        }

        //The size of the map represents the number of unique dates (days) within the specified time range, and each date corresponds to a gym visit.
        int gymVisits = trainingInfoMap.size();

        Map<String, Object> result = new HashMap<>();
        result.put("gymVisits", gymVisits);
        result.put("trainingInfo", trainingInfoMap);

        return result;
    }

    public Map<String, Object> getAverageWeightProgressByMuscleGroup(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepositoryImpl.findByUserIDAndMuscleGroupAndStartTimeBetween(
                userId, muscleGroup, startDate, endDate);

        double totalWeightProgress = 0.0;

        for (WorkoutProgress workout : userWorkouts) {
            if (userId.equals(workout.getUserId()) &&
                    workout.getStartTime() != null &&
                    (workout.getStartTime().isEqual(startDate) || workout.getStartTime().isAfter(startDate)) &&
                    (workout.getStartTime().isEqual(endDate) || workout.getStartTime().isBefore(endDate))){
                totalWeightProgress += workout.getWeight();
        }
        }


        Map<String, Object> result = new HashMap<>();
        result.put("averageWeightProgress", totalWeightProgress);
        result.put("MuscleGroup", muscleGroup);

        return result;
    }



    public WorkoutProgress getPreviousWorkout(List<WorkoutProgress> userWorkouts, WorkoutProgress currentWorkout) {
        int currentIndex = userWorkouts.indexOf(currentWorkout);
        return (currentIndex > 0) ? userWorkouts.get(currentIndex - 1) : null;
    }



}
