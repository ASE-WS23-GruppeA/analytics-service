package com.WorkoutTrackerAnalyticsService.model;
import java.time.LocalDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkoutProgress {

    private Long id;
    private Long userID;
    private Long exerciseID;
    private Long workoutSetsID;
    private Long workoutID;
    private String muscleGroup;
    private LocalDate startTime;
    private LocalDate endTime;
    private String exerciseName;
    private int reps;
    private double weight;



    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public Long getWorkoutSetsID() {
        return workoutSetsID;
    }

    public Long getWorkoutID() {
        return workoutID;
    }


    public String getMuscleGroup() {
        return muscleGroup;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userID) {
        this.userID = userID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public void setWorkoutSetsID(Long workoutSetsID) {
        this.workoutSetsID = workoutSetsID;
    }

    public void setWorkoutID(Long workoutID) {
        this.workoutID = workoutID;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    // Static method to create WorkoutProgress from JSON
    public static WorkoutProgress fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, WorkoutProgress.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for WorkoutProgress", e);
        }
    }

}

