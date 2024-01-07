package com.WorkoutTrackerAnalyticsService.controller;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import java.time.LocalDate;
import java.util.List;


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

    @PostMapping("/add-progress")
    public ResponseEntity<String> addWorkoutProgress(@RequestBody WorkoutProgress workoutProgress) {
        analyticsService.addWorkoutProgress(workoutProgress);
        return new ResponseEntity<>("Workout progress added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update-progress/{progressId}")
    public ResponseEntity<String> updateWorkoutProgress(
            @PathVariable Long progressId,
            @RequestBody WorkoutProgress updatedProgress) {
        analyticsService.updateWorkoutProgress(progressId, updatedProgress);
        return new ResponseEntity<>("Workout progress updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete-progress/{progressId}")
    public ResponseEntity<String> deleteWorkoutProgress(@PathVariable Long progressId) {
        analyticsService.deleteWorkoutProgress(progressId);
        return new ResponseEntity<>("Workout progress deleted successfully", HttpStatus.OK);
    }
}
