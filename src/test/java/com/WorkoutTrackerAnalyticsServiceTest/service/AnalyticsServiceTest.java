package com.WorkoutTrackerAnalyticsServiceTest.service;


import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import com.WorkoutTrackerAnalyticsService.repository.UserProgressRepository;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@ComponentScan(basePackages = "com.WorkoutTrackerAnalyticsService")
@SpringBootApplication
class AnalyticsServiceTest {

    @Autowired
    private AnalyticsService analyticsService;

    @MockBean
    private UserProgressRepository userProgressRepository;

    @Test
    void testGetUserProgress() {
        // Arrange
        Long userId = 1L;
        List<WorkoutProgress> mockWorkoutProgress = Collections.singletonList(new WorkoutProgress());
        when(userProgressRepository.findByUserID(String.valueOf(userId))).thenReturn(mockWorkoutProgress);

        // Act
        List<WorkoutProgress> result = analyticsService.getUserProgress(userId);

        // Assert
        assertEquals(mockWorkoutProgress, result);
    }

    @Test
    void testCalculateProgress() {
        // Arrange
        Long userId = 1L;
        LocalDateTime lastSessionTime = LocalDateTime.now().minusDays(7); // assume last session was a week ago
        List<WorkoutProgress> mockUserWorkouts = Arrays.asList(
                createWorkoutProgress(userId, LocalDateTime.now().minusDays(8)),  // session more than a week ago
                createWorkoutProgress(userId, LocalDateTime.now().minusDays(1))   // session yesterday
        );
        when(userProgressRepository.findByUserID(String.valueOf(userId))).thenReturn(mockUserWorkouts);

        // Act
        List<WorkoutProgress> result = (List<WorkoutProgress>) analyticsService.calculateProgress(userId, lastSessionTime);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // Helper method to create a WorkoutProgress instance
    private WorkoutProgress createWorkoutProgress(Long userId, LocalDateTime startTime) {
        WorkoutProgress workoutProgress = new WorkoutProgress();
        workoutProgress.setUserId(String.valueOf(userId));
        workoutProgress.setStartTime(startTime);

        return workoutProgress;
    }
}
