package com.WorkoutTrackerAnalyticsService.controller;
import com.WorkoutTrackerAnalyticsService.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/user/{userId}")
    public List<WorkoutProgress> getUserProgress(@PathVariable Long userId) {
        return analyticsService.getUserProgress(userId);
    }

    @GetMapping("/fetchData")
    public ResponseEntity<String> fetchDataFromUrl() {
        String url = ""; // need to add API my url https://example.com/api/data

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String data = response.getBody();
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to fetch data from the URL", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/calculate-progress/{userId}")
    public Map<String, Object> calculateProgress(@PathVariable Long userId,@PathVariable String lastSessionTime) {

        LocalDateTime lastSessionTimeParsed = LocalDateTime.parse(lastSessionTime);
        return  analyticsService.calculateProgress(userId, lastSessionTimeParsed);
    }
}
