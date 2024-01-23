package com.WorkoutTrackerAnalyticsService.service;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final UserProgressRepository userProgressRepository;
    private final WorkoutRepository workoutService;
    private final ExerciseRepository exerciseService;

    public AnalyticsServiceImpl(UserProgressRepository userProgressRepository, WorkoutRepository workoutService,  ExerciseRepository exerciseService) {
        this.userProgressRepository = userProgressRepository;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
    }

    public Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndExerciseNameAndStartTimeBetween(
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
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndStartTimeBetween(
                userId, startDate, endDate);

        Map<LocalDate, List<Map<String, Object>>> trainingInfoMap = new HashMap<>();

        for (WorkoutProgress workout : userWorkouts) {
            if (userId.equals(workout.getUserId()) &&
                    workout.getStartTime() != null &&
                    (workout.getStartTime().isEqual(startDate) || workout.getStartTime().isAfter(startDate)) &&
                    (workout.getStartTime().isEqual(endDate) || workout.getStartTime().isBefore(endDate))) {
                LocalDate workoutDate = workout.getStartTime();

                Map<String, Object> exerciseInfo = new HashMap<>();
                exerciseInfo.put("exercise", workout.getExerciseName());
                exerciseInfo.put("sets", workout.getWorkoutSetsID());
                exerciseInfo.put("reps", workout.getReps());

                trainingInfoMap.computeIfAbsent(workoutDate, k -> new ArrayList<>()).add(exerciseInfo);
            }
        }

        // Sort the map by dates
        trainingInfoMap = trainingInfoMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // The size of the map represents the number of unique dates (days) within the specified time range,
        // and each date corresponds to a gym visit.
        int gymVisits = trainingInfoMap.size();

        Map<String, Object> result = new HashMap<>();
        result.put("gymVisits", gymVisits);
        result.put("trainingInfo", trainingInfoMap);

        return result;
    }


    public Map<String, Object> getAverageWeightProgressByMuscleGroup(Long userId, String muscleGroup, LocalDate startDate, LocalDate endDate) {
        List<WorkoutProgress> userWorkouts = userProgressRepository.findByUserIDAndMuscleGroupAndStartTimeBetween(
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
