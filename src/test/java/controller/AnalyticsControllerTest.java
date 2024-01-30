package controller;

import com.WorkoutTrackerAnalyticsService.controller.AnalyticsController;
import com.WorkoutTrackerAnalyticsService.service.impl.AnalyticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AnalyticsControllerTest {

    @Mock
    private AnalyticsServiceImpl analyticsService;

    @InjectMocks
    private AnalyticsController analyticsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeightProgress() {

        when(analyticsService.getWeightProgressForExercise(any(), any(), any(), any()))
                .thenReturn(Collections.singletonMap("date", 10.0));


        ResponseEntity<Map<String, Double>> response = analyticsController.getWeightProgress(1L, "Exercise", LocalDate.now(), LocalDate.now());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("date", 10.0), response.getBody());
    }

    @Test
    void testGetUserTrainingInfo() {

        when(analyticsService.getUserTrainingInfo(any(), any(), any()))
                .thenReturn(Collections.singletonMap("gymVisits", 1));


        ResponseEntity<Map<String, Object>> response = analyticsController.getUserTrainingInfo(1L, LocalDate.now(), LocalDate.now());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("gymVisits", 1), response.getBody());
    }

    @Test
    void testGetAverageWeightProgress() {

        when(analyticsService.getAverageWeightProgressByMuscleGroup(any(), any(), any(), any()))
                .thenReturn(Collections.singletonMap("averageWeightProgress", 15.0));


        ResponseEntity<Map<String, Object>> response = analyticsController.getAverageWeightProgress(1L, "MuscleGroup", LocalDate.now(), LocalDate.now());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("averageWeightProgress", 15.0), response.getBody());
    }
}
