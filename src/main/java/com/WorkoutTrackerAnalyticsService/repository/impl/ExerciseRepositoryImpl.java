package com.WorkoutTrackerAnalyticsService.repository.impl;

import java.util.Arrays;
import java.util.List;

import com.WorkoutTrackerAnalyticsService.dto.ExerciseDTO;
import com.WorkoutTrackerAnalyticsService.repository.ExerciseRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExerciseRepositoryImpl implements ExerciseRepository {

  private final RestTemplate restTemplate;

  @Value("${api.exercise-service.base-url}")
  private String exerciseServiceUrl;

  public ExerciseRepositoryImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<ExerciseDTO> getAllExercises() {
    String url = exerciseServiceUrl + "/exercises";
    ExerciseDTO[] responseArray = restTemplate.getForObject(url, ExerciseDTO[].class);
    return Arrays.asList(responseArray);
  }

  @Override
  public ExerciseDTO getExerciseById(Long exerciseId) {
    String url = exerciseServiceUrl + "/exercises/" + exerciseId;
    return restTemplate.getForObject(url, ExerciseDTO.class);
  }

}