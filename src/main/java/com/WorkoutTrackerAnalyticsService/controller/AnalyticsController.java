package com.WorkoutTrackerAnalyticsService.controller;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/user-progress/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getUserProgress(@PathVariable Long userId) {
        List<WorkoutProgress> userProgress = analyticsService.getUserProgress(userId);
        return new ResponseEntity<>(userProgress, HttpStatus.OK);
    }

    @GetMapping("/workout-progress/{workoutId}")
    public ResponseEntity<List<WorkoutProgress>> getWorkoutProgress(@PathVariable Long workoutId) {
        List<WorkoutProgress> workoutProgress = analyticsService.getWorkoutProgress(workoutId);
        return new ResponseEntity<>(workoutProgress, HttpStatus.OK);
    }

    @GetMapping("/exercise-progress/{exerciseId}")
    public ResponseEntity<List<WorkoutProgress>> getExerciseProgress(@PathVariable Long exerciseId) {
        List<WorkoutProgress> exerciseProgress = analyticsService.getExerciseProgress(exerciseId);
        return new ResponseEntity<>(exerciseProgress, HttpStatus.OK);
    }

    @GetMapping("/progress-by-date/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getProgressByDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<WorkoutProgress> progressByDate = analyticsService.getProgressByDate(userId, startDate.atStartOfDay(), endDate.atStartOfDay());
        return new ResponseEntity<>(progressByDate, HttpStatus.OK);
    }

    @GetMapping("/progress-by-muscle-group/{userId}")
    public ResponseEntity<List<WorkoutProgress>> getProgressByMuscleGroup(
            @PathVariable Long userId,
            @RequestParam String muscleGroup) {
        List<WorkoutProgress> progressByMuscleGroup = analyticsService.getProgressByMuscleGroup(userId, muscleGroup);
        return new ResponseEntity<>(progressByMuscleGroup, HttpStatus.OK);
    }

    @GetMapping("/total-volume/{userId}")
    public ResponseEntity<Double> getTotalVolume(@PathVariable Long userId) {
        Double totalVolume = analyticsService.getTotalVolume(userId);
        return new ResponseEntity<>(totalVolume, HttpStatus.OK);
    }

    @GetMapping("/weight-progress/{userId}/{exerciseName}")
    public ResponseEntity<Map<LocalDateTime, Double>> getWeightProgress(
            @PathVariable Long userId,
            @PathVariable String exerciseName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        List<WorkoutProgress> weightProgress = (List<WorkoutProgress>) analyticsService.getWeightProgressForExercise(userId, exerciseName, startDate, endDate);
        Map<LocalDateTime, Double> result = weightProgress.stream()
                .collect(Collectors.toMap(WorkoutProgress::getStartTime, WorkoutProgress::getWeight));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user-training-info/{userId}")
    public ResponseEntity<Map<String, Object>> getUserTrainingInfo(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        Map<String, Object> userTrainingInfo = analyticsService.getUserTrainingInfo(userId, startDate, endDate);
        return new ResponseEntity<>(userTrainingInfo, HttpStatus.OK);
    }


    @GetMapping("/average-weight-progress/{userId}/{muscleGroup}")
    public ResponseEntity<Map<String, Double>> getAverageWeightProgress(
            @PathVariable Long userId,
            @PathVariable String muscleGroup,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        Map<String, Double> averageWeightProgress = analyticsService.getAverageWeightProgressByMuscleGroup(userId, muscleGroup, startDate, endDate);
        return new ResponseEntity<>(averageWeightProgress, HttpStatus.OK);
    }

}
