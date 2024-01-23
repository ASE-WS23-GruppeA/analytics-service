package com.WorkoutTrackerAnalyticsService.repository;

import com.WorkoutTrackerAnalyticsService.repository.WorkoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WorkoutRepository {

    private final RestTemplate restTemplate;

    @Value("${workout.service.url}")
    private String workoutServiceUrl;

    @Autowired
    public WorkoutRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WorkoutDTO> getAllWorkoutsForUser() {
        String url = workoutServiceUrl + "/workouts/user/:userID";
        WorkoutDTO[] responseArray = restTemplate.getForObject(url, WorkoutDTO[].class);
        return Arrays.asList(responseArray);
    }

    public List<Map<String, Object>> getLastWorkoutsForUser(Long userId) {
        String url = workoutServiceUrl + "/workouts/last/:userID";
        Map<String, Object>[] responseArray = restTemplate.getForObject(url, Map[].class);
        return Arrays.asList(responseArray);
    }

    public Map<String, Object> getWorkoutByNameForUser(Long userId, String workoutName) {
        String url = workoutServiceUrl + "/workouts/user/" + userId + "/" + workoutName;
        return restTemplate.getForObject(url, Map.class);
    }

}