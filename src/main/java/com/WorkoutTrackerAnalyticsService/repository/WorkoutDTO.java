package com.WorkoutTrackerAnalyticsService.repository;

import java.util.List;
import java.util.Objects;

public class WorkoutDTO {
    private long userID;
    private long workoutID;
    private String workoutName;
    private String createdDate;

    private List<WorkoutSetDTO> workoutSets;

    public WorkoutDTO() {
    }

    public WorkoutDTO(long userID, long workoutID, String workoutName, String createdDate, List<WorkoutSetDTO> workoutSets) {
        this.userID = userID;
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.createdDate = createdDate;
        this.workoutSets = workoutSets;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) `{
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutDTO that = (WorkoutDTO) o;
        return userID == that.userID && workoutID == that.workoutID && Objects.equals(workoutName, that.workoutName) && Objects.equals(createdDate, that.createdDate) && Objects.equals(workoutSets, that.workoutSets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, workoutID, workoutName, createdDate, workoutSets);
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
