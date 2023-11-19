package com.WorkoutTrackerAnalyticsServiceTest.controller;

import com.WorkoutTrackerAnalyticsService.controller.AnalyticsController;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void testGetUserProgress() throws Exception {
        // Mock data
        List<WorkoutProgress> mockWorkoutProgress = Collections.singletonList(new WorkoutProgress());
        when(analyticsService.getUserProgress(1L)).thenReturn(mockWorkoutProgress);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/analytics/user/{userId}", 1L));

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    void testFetchDataFromUrl() throws Exception {
        // Mock external API response
        String mockApiResponse = "{'data':'mocked-data'}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity("https://example.com/api/data", String.class)).thenReturn(mockResponseEntity);

        // Perform the GET request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/analytics/fetchData"));

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mockApiResponse));
    }
}
