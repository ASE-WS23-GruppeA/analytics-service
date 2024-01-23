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
public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final RestTemplate restTemplate;

    @Value("${workout.service.url}")
    private String workoutServiceUrl;

    @Autowired
    public WorkoutRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WorkoutDTO> getAllWorkoutsForUser(Long UserID) {
        String url = workoutServiceUrl + "/workouts/user/:"+UserID;
        WorkoutDTO[] responseArray = restTemplate.getForObject(url, WorkoutDTO[].class);
        return Arrays.asList(responseArray);
    }

    public WorkoutDTO getLastWorkoutForUser(Long UserID) {
        String url = workoutServiceUrl + "/workouts/last/:"+UserID;
      WorkoutDTO responseArray = restTemplate.getForObject(url, WorkoutDTO.class);
        return responseArray;
    }

    public List<WorkoutDTO> getWorkoutByName(Long userId, String workoutName) {
        String url = workoutServiceUrl + "/workouts/user/:" + userId + "/:" + workoutName;
        WorkoutDTO[] responseArray = restTemplate.getForObject(url, WorkoutDTO[].class);
        return Arrays.asList(responseArray);

    }

}