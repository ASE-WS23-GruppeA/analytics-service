package com.WorkoutTrackerAnalyticsService.controller;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/user/{userId}")
    public List<WorkoutProgress> getUserProgress(@PathVariable Long userId) {
        return analyticsService.getUserProgress(userId);
    }

    @GetMapping("/fetchData")
    public ResponseEntity<String> fetchDataFromUrl() {
        // Use RestTemplate or HttpClient for getting and working with JSON data.
        // return the result as ResponseEntity.
        return null;
    }


}
