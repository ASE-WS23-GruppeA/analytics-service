package com.WorkoutTrackerAnalyticsService.service;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public AnalyticsServiceImpl(WorkoutRepositoryImpl workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }


    public Map<String, Double> getWeightProgressForExercise(Long userId, String exerciseName, LocalDate startDate, LocalDate endDate) {
        List<WorkoutDTO> userWorkouts = workoutRepository.getAllWorkoutsForUser(userId);
        List<ExerciseDTO> userExercises = exerciseRepository.getAllExercises();

        // Step 1: Create a mapping of exercise names to IDs
        Map<String, Long> exerciseIdMap = userExercises.stream()
                .collect(Collectors.toMap(ExerciseDTO::getExerciseName, ExerciseDTO::getExerciseID));

        Map<String, Double> weightProgressMap = new LinkedHashMap<>();

        for (WorkoutDTO workout : userWorkouts) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            LocalDate workoutDate = LocalDate.parse(workout.getCreatedDate(), formatter);

            if ((workoutDate.isEqual(startDate) || workoutDate.isAfter(startDate)) &&
                    (workoutDate.isEqual(endDate) || workoutDate.isBefore(endDate))) {

                for (WorkoutSetDTO set : workout.getWorkoutSets()) {
                    // Step 2: Get the exercise ID based on the exercise name
                    Long exerciseId = exerciseIdMap.get(exerciseName);

                    // Check if the set's exercise ID matches the desired exercise ID
                    if (exerciseId != null && Objects.equals(set.getExerciseID(), exerciseId)) {
                        double weight = set.getWeights();
                        weightProgressMap.put(workoutDate.toString(), weight);
                    }
                }
            }
        }
        return weightProgressMap;
    }


    public Map<String, Object> getUserTrainingInfo(Long userId, LocalDate startDate, LocalDate endDate) {
        List<WorkoutDTO> userWorkouts = workoutRepository.getAllWorkoutsForUser(userId);

        Map<LocalDate, List<Map<String, Object>>> trainingInfoMap = new HashMap<>();

        for (WorkoutDTO workout : userWorkouts) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            LocalDate workoutDate = LocalDate.parse(workout.getCreatedDate(), formatter);


            if (userId.equals(workout.getUserID()) &&
                    (workoutDate.isEqual(startDate) || workoutDate.isAfter(startDate)) &&
                    (workoutDate.isEqual(endDate) || workoutDate.isBefore(endDate))) {

                for (WorkoutSetDTO set : workout.getWorkoutSets()) {
                    Map<String, Object> exerciseInfo = new HashMap<>();
                    exerciseInfo.put("exercise", exerciseRepository.getExerciseById(set.getExerciseID()).getExerciseName());
                    exerciseInfo.put("sets", set.getWorkoutSetsID());
                    exerciseInfo.put("reps", set.getReps());

                    trainingInfoMap.computeIfAbsent(workoutDate, k -> new ArrayList<>()).add(exerciseInfo);
                }
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
        List<WorkoutDTO> userWorkouts = workoutRepository.getAllWorkoutsForUser(userId);

        double totalWeightProgress = 0.0;
        int count = 0;

        for (WorkoutDTO workout : userWorkouts) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            LocalDate workoutDate = LocalDate.parse(workout.getCreatedDate(), formatter);


            if (userId.equals(workout.getUserID()) &&
                    (workoutDate.isEqual(startDate) || workoutDate.isAfter(startDate)) &&
                    (workoutDate.isEqual(endDate) || workoutDate.isBefore(endDate))) {
                for (WorkoutSetDTO set : workout.getWorkoutSets()) {
                    if (exerciseRepository.getExerciseById(set.getExerciseID()).getMuscleGroup().equals(muscleGroup)) {
                        totalWeightProgress += set.getWeights();
                        count++;
                    }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        if (count > 0) {
            result.put("averageWeightProgress", totalWeightProgress / count);
        } else {
            result.put("averageWeightProgress", 0.0);
        }
        result.put("MuscleGroup", muscleGroup);

        return result;
    }


}
