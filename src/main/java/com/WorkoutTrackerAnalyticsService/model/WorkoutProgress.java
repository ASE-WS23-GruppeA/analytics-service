package com.WorkoutTrackerAnalyticsService.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data // automatically generates get and set methods for this class
public class WorkoutProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String exerciseName;
    private int reps;
    private double weight;


}
