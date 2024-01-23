package com.WorkoutTrackerAnalyticsService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseRepository {

    private RestTemplate restTemplate;

    @Value("${exercise.service.url}")
    private String exerciseServiceUrl;

    @Autowired
    public ExerciseRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Map<String, Object>> getAllExercises() {
        String url = exerciseServiceUrl + "/exercises";
        Map<String, Object>[] responseArray = restTemplate.getForObject(url, Map[].class);
        return Arrays.asList(responseArray);
    }

    public Map<String, Object> getExerciseById(Long exerciseId) {
        String url = exerciseServiceUrl + "/exercises/" + exerciseId;
        return restTemplate.getForObject(url, Map.class);
    }
}