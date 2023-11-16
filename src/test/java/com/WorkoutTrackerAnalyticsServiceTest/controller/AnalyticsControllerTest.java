package com.WorkoutTrackerAnalyticsServiceTest.controller;


import com.WorkoutTrackerAnalyticsService.controller.AnalyticsController;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void testGetUserProgress() throws Exception {
        // Implement your test logic here
        // Use mockMvc to perform HTTP requests and verify the responses
    }

    @Test
    void testFetchDataFromUrl() throws Exception {
        // Implement your test logic here
    }
}
