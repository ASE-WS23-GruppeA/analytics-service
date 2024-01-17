package com.WorkoutTrackerAnalyticsService.service;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    private final UserProgressRepository userProgressRepository;

    @Autowired
    public AnalyticsService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public List<WorkoutProgress> getUserProgress(Long workoutId) {
        return userProgressRepository.findByWorkoutID(workoutId);
    }


    private double calculateWorkoutProgress(WorkoutProgress currentWorkout, WorkoutProgress previousWorkout) {
        // Calculate overall progress for each workout session (in kg)
        double progress = 0.0;
        if (currentWorkout != null && previousWorkout != null) {
            progress = currentWorkout.getWeight() - previousWorkout.getWeight();
        }
        return progress;
    }

    private Map<String, Double> calculateExerciseProgress(WorkoutProgress currentWorkout, WorkoutProgress previousWorkout) {
        Map<String, Double> exerciseProgress = new HashMap<>();

        if (currentWorkout != null && previousWorkout != null) {
            // Calculate overall progress for each exercise by the weight progression
            double progress = currentWorkout.getWeight() - previousWorkout.getWeight();

            exerciseProgress.put(currentWorkout.getExerciseName(), progress);
        }

        return exerciseProgress;
    }


    public void addWorkoutProgress(WorkoutProgress workoutProgress) {
        userProgressRepository.save(workoutProgress);
    }

    public void updateWorkoutProgress(Long progressId, WorkoutProgress updatedProgress) {
        WorkoutProgress existingProgress = userProgressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Workout progress not found with id: " + progressId));

        existingProgress.setReps(updatedProgress.getReps());
        existingProgress.setWeight(updatedProgress.getWeight());
        existingProgress.setMuscleGroup(updatedProgress.getMuscleGroup());
        existingProgress.setStartTime(updatedProgress.getStartTime());
        existingProgress.setEndTime(updatedProgress.getEndTime());
        userProgressRepository.save(existingProgress);
    }

    public void deleteWorkoutProgress(Long progressId) {
        userProgressRepository.deleteById(progressId);
    }

    public Double getTotalVolume(Long userId) {
        List<WorkoutProgress> userProgress = userProgressRepository.findByUserID(String.valueOf(userId));
        return userProgress.stream().mapToDouble(WorkoutProgress::getWeight).sum();
    }

    public List<WorkoutProgress> getProgressByMuscleGroup(Long userId, String muscleGroup) {
        return userProgressRepository.findByUserIDAndMuscleGroup(String.valueOf(userId), muscleGroup);
    }

    public List<WorkoutProgress> getProgressByDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return userProgressRepository.findByUserIDAndStartTimeBetween(String.valueOf(userId), startDate, endDate);
    }

    public List<WorkoutProgress> getExerciseProgress(Long exerciseId) {
        return userProgressRepository.findByExerciseID(exerciseId);
    }

    public List<WorkoutProgress> getWorkoutProgress(Long workoutId) {
        return userProgressRepository.findByWorkoutID(workoutId);
    }

    public Map<String, Object> calculateProgress(Long userId, LocalDate lastSessionTime) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserID(String.valueOf(userId));

        Map<LocalDate, Double> workoutProgressMap = new HashMap<>();
        Map<String, Double> exerciseProgressMap = new HashMap<>();


        for (int i = 1; i < userWorkouts.size(); i++) {

            WorkoutProgress currentWorkout = userWorkouts.get(i);
            WorkoutProgress previousWorkout = userWorkouts.get(i - 1);

            // Check if the training was after the lastTime
            if (currentWorkout.getStartTime().isAfter(lastSessionTime)) {

                double workoutProgress = calculateWorkoutProgress(currentWorkout, previousWorkout);

                // Calculate overall progress for each exercise
                Map<String, Double> exerciseProgress = calculateExerciseProgress(currentWorkout, previousWorkout);

                // Add progress for current workout in the map
                workoutProgressMap.put(currentWorkout.getStartTime(), workoutProgress);

                //Update the progress map for each exercise
                exerciseProgress.forEach((exerciseName, progress) ->
                        exerciseProgressMap.merge(exerciseName, progress, Double::sum)
                );
            }
        }
        Map<String, Object> progressData = new HashMap<>();
        progressData.put("workoutProgress", workoutProgressMap);
        progressData.put("exerciseProgress", exerciseProgressMap);

        return progressData;
    }

    public Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndExerciseNameAndStartTimeBetween(
                String.valueOf(userId), exerciseName, startDate, endDate);

        Map<String, Double> weightProgressMap = new HashMap<>();

        for (int i = 1; i < userWorkouts.size(); i++) {
            WorkoutProgress currentWorkout = userWorkouts.get(i);
            WorkoutProgress previousWorkout = userWorkouts.get(i - 1);

            double weightProgress = calculateWorkoutProgress(currentWorkout, previousWorkout);
            weightProgressMap.put(currentWorkout.getStartTime().toString(), weightProgress);
        }

        return weightProgressMap;
    }

    public Map<String, Object> getUserTrainingInfo(Long userId, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndStartTimeBetween(
                String.valueOf(userId), startDate, endDate);

        Map<LocalDate, List<String>> trainingInfoMap = new HashMap<>();

        for (WorkoutProgress workout : userWorkouts) {
            LocalDate workoutDate = workout.getStartTime();
            String trainingInfo = workout.getExerciseName() + " - Sets: " + workout.getWorkoutSetsID() + ", Reps: " + workout.getReps();

            trainingInfoMap.computeIfAbsent(workoutDate, k -> new ArrayList<>()).add(trainingInfo);
        }

        //The size of the map represents the number of unique dates (days) within the specified time range, and each date corresponds to a gym visit.
        int gymVisits = trainingInfoMap.size();

        Map<String, Object> result = new HashMap<>();
        result.put("gymVisits", gymVisits);
        result.put("trainingInfo", trainingInfoMap);

        return result;
    }

    public Map<String, Double> getAverageWeightProgressByMuscleGroup(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndMuscleGroupAndStartTimeBetween(
                String.valueOf(userId), muscleGroup, startDate, endDate);

        Map<String, Double> averageWeightProgressMap = new HashMap<>();
        Map<String, Integer> exerciseCountMap = new HashMap<>();

        for (WorkoutProgress workout : userWorkouts) {
            String exerciseName = workout.getExerciseName();
            double weightProgress = calculateWorkoutProgress(workout, getPreviousWorkout(userWorkouts, workout));

            averageWeightProgressMap.merge(exerciseName, weightProgress, Double::sum);
            exerciseCountMap.merge(exerciseName, 1, Integer::sum);
        }

        averageWeightProgressMap.forEach((exerciseName, totalWeightProgress) ->
                averageWeightProgressMap.put(exerciseName, totalWeightProgress / exerciseCountMap.get(exerciseName)));

        return averageWeightProgressMap;
    }

    private WorkoutProgress getPreviousWorkout(List<WorkoutProgress> userWorkouts, WorkoutProgress currentWorkout) {
        int currentIndex = userWorkouts.indexOf(currentWorkout);
        return (currentIndex > 0) ? userWorkouts.get(currentIndex - 1) : null;
    }



}
