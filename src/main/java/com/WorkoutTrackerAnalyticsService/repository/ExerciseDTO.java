package com.WorkoutTrackerAnalyticsService.repository;

public class ExerciseDTO {
    private Long exerciseID;
    private String exerciseName;
    private String muscleGroup;

    public Long getExerciseByID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public ExerciseDTO(Long exerciseID, String exerciseName, String muscleGroup) {
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
    }

    public ExerciseDTO() {
    }
}
