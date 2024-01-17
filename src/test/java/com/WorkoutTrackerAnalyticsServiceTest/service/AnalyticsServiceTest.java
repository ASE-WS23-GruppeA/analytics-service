package com.WorkoutTrackerAnalyticsServiceTest.service;

import com.WorkoutTrackerAnalyticsService.WorkoutTrackerAnalyticsServiceApplication;
import com.WorkoutTrackerAnalyticsService.controller.AnalyticsController;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = WorkoutTrackerAnalyticsServiceApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AnalyticsController analyticsController;

    @Mock
    private AnalyticsService analyticsService;

    @Test
    void testGetUserTrainingInfo() throws Exception {
        // Mocking the service response
        List<WorkoutProgress> mockWorkouts = new ArrayList<>();
        // Add some mock workout data
        mockWorkouts.add(new WorkoutProgress(/* Set your mock data here */));
        when(analyticsService.getUserTrainingInfo(any(Long.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(createMockUserTrainingInfo());

        // Perform the GET request and verify the response
        mockMvc.perform(get("/analytics/user-training-info/123")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gymVisits").value(1)) // Adjust based on your mock data
                .andExpect(jsonPath("$.trainingInfo").isMap()) // Adjust based on your mock data
                .andExpect(jsonPath("$.trainingInfo['2023-01-01']").isArray()); // Adjust based on your mock data

    }

    private Map<String, Object> createMockUserTrainingInfo() {
        // Create and return mock user training info data
        // Adjust the data based on your expectations
        return Map.of(
                "gymVisits", 1,
                "trainingInfo", Map.of("2023-01-01", List.of("Mock Exercise - Sets: 3, Reps: 10"))
        );
    }
}
