package com.WorkoutTrackerAnalyticsServiceTest.model;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkoutProgressTest {

    @Test
    void testGetterAndSetter() {
        // Create a mock API response
        String mockApiResponse = "{'id':1,'userId':'user123','exerciseID':2,'workoutSetsID':3,'muscleGroup':'Chest','startTime':'2023-01-01T12:00:00','endTime':'2023-01-01T13:00:00','exerciseName':'Bench Press','reps':10,'weight':100.0}";

        // Convert the mock API response to WorkoutProgress
        WorkoutProgress workoutProgress = WorkoutProgress.fromJson(mockApiResponse);

        // Assert values using getters
        assertEquals(1L, workoutProgress.getId());
        assertEquals("user123", workoutProgress.getUserID());
        assertEquals(2L, workoutProgress.getExerciseID());
        assertEquals(3L, workoutProgress.getWorkoutSetsID());
        assertEquals("Chest", workoutProgress.getMuscleGroup());
        assertEquals(LocalDateTime.parse("2023-01-01T12:00:00"), workoutProgress.getStartTime());
        assertEquals(LocalDateTime.parse("2023-01-01T13:00:00"), workoutProgress.getEndTime());
        assertEquals("Bench Press", workoutProgress.getExerciseName());
        assertEquals(10, workoutProgress.getReps());
        assertEquals(100.0, workoutProgress.getWeight());


    }
}
