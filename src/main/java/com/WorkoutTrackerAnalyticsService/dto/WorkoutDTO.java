package com.WorkoutTrackerAnalyticsService.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkoutDTO {

  private long userID;
  private long workoutID;
  private String workoutName;
  private String createdDate;

  @JsonProperty("workout_sets")
  private List<WorkoutSetDTO> workoutSets;

  public long getUserID() {
    return userID;
  }

  public void setUserID(long userID) {
    this.userID = userID;
  }

  public long getWorkoutID() {
    return workoutID;
  }

  public void setWorkoutID(long workoutID) {
    this.workoutID = workoutID;
  }

  public String getWorkoutName() {
    return workoutName;
  }

  public void setWorkoutName(String workoutName) {
    this.workoutName = workoutName;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public List<WorkoutSetDTO> getWorkoutSets() {
    return workoutSets;
  }

  public void setWorkoutSets(List<WorkoutSetDTO> workoutSets) {
    this.workoutSets = workoutSets;
  }

}
