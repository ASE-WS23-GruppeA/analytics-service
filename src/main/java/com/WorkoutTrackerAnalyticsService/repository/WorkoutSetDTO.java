package com.WorkoutTrackerAnalyticsService.repository;

public class WorkoutSetDTO {
    private Long workoutSetsID;
    private Long workoutID;
    private Long exerciseID;

    private Long reps;
    private Long weights;

    public WorkoutSetDTO() {
    }

    public WorkoutSetDTO(Long workoutSetsID, Long workoutID, Long exerciseID, Long reps, Long weights) {
        this.workoutSetsID = workoutSetsID;
        this.workoutID = workoutID;
        this.exerciseID = exerciseID;
        this.reps = reps;
        this.weights = weights;
    }

    public Long getWorkoutSetsID() {
        return workoutSetsID;
    }

    public void setWorkoutSetsID(Long workoutSetsID) {
        this.workoutSetsID = workoutSetsID;
    }

    public Long getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(Long workoutID) {
        this.workoutID = workoutID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public Long getReps() {
        return reps;
    }

    public void setReps(Long reps) {
        this.reps = reps;
    }

    public Long getWeights() {
        return weights;
    }

    public void setWeights(Long weights) {
        this.weights = weights;
    }
}
