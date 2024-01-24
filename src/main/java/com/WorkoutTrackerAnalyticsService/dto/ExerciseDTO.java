package com.WorkoutTrackerAnalyticsService.dto;

public class ExerciseDTO {
  private Long exerciseID;
  private String exerciseName;
  private String muscleGroup;

  public Long getExerciseID() {
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

}
