package com.WorkoutTrackerAnalyticsService.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WorkoutProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userID;
    private Long exerciseID;
    private Long workoutSetsID;
    private Long workoutID;
    private String muscleGroup;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String exerciseName;
    private int reps;
    private double weight;

    // Getters
    public Long getId() {
        return id;
    }

    public String getUserID() {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
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

    public void setUserId(String userID) {
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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


}

