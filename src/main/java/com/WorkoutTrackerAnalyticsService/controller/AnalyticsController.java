package com.WorkoutTrackerAnalyticsService.controller;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsServiceImpl analyticsServiceImpl;

    public AnalyticsController(AnalyticsServiceImpl analyticsServiceImpl) {
        this.analyticsServiceImpl = analyticsServiceImpl;
    }

    @GetMapping("/user-progress/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getUserProgress(@PathVariable Long userId) {
        List<WorkoutProgress> userProgress = analyticsServiceImpl.getUserProgress(userId);
        return new ResponseEntity<>(userProgress, HttpStatus.OK);
    }

    @GetMapping("/workout-progress/{workoutId}")
    public ResponseEntity<List<WorkoutProgress>> getWorkoutProgress(@PathVariable Long workoutId) {
        List<WorkoutProgress> workoutProgress = analyticsServiceImpl.getWorkoutProgress(workoutId);
        return new ResponseEntity<>(workoutProgress, HttpStatus.OK);
    }

    @GetMapping("/exercise-progress/{exerciseId}")
    public ResponseEntity<List<WorkoutProgress>> getExerciseProgress(@PathVariable Long exerciseId) {
        List<WorkoutProgress> exerciseProgress = analyticsServiceImpl.getExerciseProgress(exerciseId);
        return new ResponseEntity<>(exerciseProgress, HttpStatus.OK);
    }

    @GetMapping("/progress-by-date/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getProgressByDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<WorkoutProgress> progressByDate = analyticsServiceImpl.getProgressByDate(userId, startDate, endDate);
        return new ResponseEntity<>(progressByDate, HttpStatus.OK);
    }

    @GetMapping("/progress-by-muscle-group/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getProgressByMuscleGroup(
            @PathVariable Long userId,
            @RequestParam String muscleGroup) {
        List<WorkoutProgress> progressByMuscleGroup = analyticsServiceImpl.getProgressByMuscleGroup(userId, muscleGroup);
        return new ResponseEntity<>(progressByMuscleGroup, HttpStatus.OK);
    }

    @GetMapping("/total-volume/{userId}")
    public ResponseEntity<Double> getTotalVolume(@PathVariable Long userId) {
        Double totalVolume = analyticsServiceImpl.getTotalVolume(userId);
        return new ResponseEntity<>(totalVolume, HttpStatus.OK);
    }

    @GetMapping("/weight-progress/{userId}/{exerciseName}")
    public ResponseEntity<Map<LocalDate, Double>> getWeightProgress(
            @PathVariable Long userId,
            @PathVariable String exerciseName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<WorkoutProgress> weightProgress = (List<WorkoutProgress>) analyticsServiceImpl.getWeightProgressForExercise(userId, exerciseName, startDate, endDate);
        Map<LocalDate, Double> result = weightProgress.stream()
                .collect(Collectors.toMap(WorkoutProgress::getStartTime, WorkoutProgress::getWeight));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user-training-info/{userId}")
    public ResponseEntity<Map<String, Object>> getUserTrainingInfo(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> userTrainingInfo = analyticsServiceImpl.getUserTrainingInfo(userId, startDate, endDate );
        return new ResponseEntity<>(userTrainingInfo, HttpStatus.OK);
    }

    @GetMapping("/average-weight-progress/{userId}/{muscleGroup}")
    public ResponseEntity<Map<String, Double>> getAverageWeightProgress(
            @PathVariable Long userId,
            @PathVariable String muscleGroup,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Double> averageWeightProgress = analyticsServiceImpl.getAverageWeightProgressByMuscleGroup(userId, muscleGroup, startDate, endDate);
        return new ResponseEntity<>(averageWeightProgress, HttpStatus.OK);
    }

}
