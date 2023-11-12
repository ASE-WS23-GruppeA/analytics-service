package com.WorkoutTrackerAnalyticsService.service;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;
import java.time.LocalDateTime;
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


    public Map<String, Object> calculateProgress(Long userId, LocalDateTime lastSessionTime) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserID(String.valueOf(userId));

        Map<LocalDateTime, Double> workoutProgressMap = new HashMap<>();
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
}
