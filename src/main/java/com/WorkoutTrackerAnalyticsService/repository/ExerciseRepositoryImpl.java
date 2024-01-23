package com.WorkoutTrackerAnalyticsService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseRepositoryImpl implements ExerciseRepository {

    private RestTemplate restTemplate;

    @Value("${exercise.service.url}")
    private String exerciseServiceUrl;

    @Autowired
    public ExerciseRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<ExerciseDTO> getAllExercises() {
        String url = exerciseServiceUrl + "/exercises";
        ExerciseDTO[] responseArray = restTemplate.getForObject(url, ExerciseDTO[].class);
        return Arrays.asList(responseArray);
    }

    public ExerciseDTO getExerciseById(Long exerciseId) {
        String url = exerciseServiceUrl + "/exercises/" + exerciseId;
        return restTemplate.getForObject(url, ExerciseDTO.class);
    }
}