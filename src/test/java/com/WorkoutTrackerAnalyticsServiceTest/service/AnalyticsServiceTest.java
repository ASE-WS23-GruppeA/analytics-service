package com.WorkoutTrackerAnalyticsServiceTest.service;


import com.WorkoutTrackerAnalyticsService.repository.UserProgressRepository;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

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
        // Implement your test logic here
        // Mock the behavior of userProgressRepository and assert the service behavior
    }

    @Test
    void testCalculateProgress() {
        // Implement your test logic here
        // Mock the behavior of userProgressRepository and assert the service behavior
    }
}
