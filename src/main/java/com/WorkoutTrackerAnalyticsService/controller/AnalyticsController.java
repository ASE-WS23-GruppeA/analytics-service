package com.WorkoutTrackerAnalyticsService.controller;

import java.time.LocalDate;
import java.util.Map;

import com.WorkoutTrackerAnalyticsService.service.impl.AnalyticsServiceImpl;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
  private final AnalyticsServiceImpl analyticsServiceImpl;

  public AnalyticsController(AnalyticsServiceImpl analyticsServiceImpl) {
    this.analyticsServiceImpl = analyticsServiceImpl;
  }

  @GetMapping("/weight-progress/{userId}/{exerciseName}")
  public ResponseEntity<Map<String, Double>> getWeightProgress(
      @PathVariable Long userId,
      @PathVariable String exerciseName,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    Map<String, Double> weightProgress = analyticsServiceImpl.getWeightProgressForExercise(userId, exerciseName,
        startDate, endDate
    );
    return new ResponseEntity<>(weightProgress, HttpStatus.OK);
  }

  @GetMapping("/user-training-info/{userId}")
  public ResponseEntity<Map<String, Object>> getUserTrainingInfo(
      @PathVariable Long userId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    Map<String, Object> userTrainingInfo = analyticsServiceImpl.getUserTrainingInfo(userId, startDate, endDate);
    return new ResponseEntity<>(userTrainingInfo, HttpStatus.OK);
  }

  @GetMapping("/average-weight-progress/{userId}/{muscleGroup}")
  public ResponseEntity<Map<String, Object>> getAverageWeightProgress(
      @PathVariable Long userId,
      @PathVariable String muscleGroup,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    Map<String, Object> averageWeightProgress = analyticsServiceImpl.getAverageWeightProgressByMuscleGroup(userId,
        muscleGroup, startDate, endDate
    );
    return new ResponseEntity<>(averageWeightProgress, HttpStatus.OK);
  }

}
