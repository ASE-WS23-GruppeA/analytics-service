package com.WorkoutTrackerAnalyticsService.repository.impl;

import java.util.Arrays;
import java.util.List;

import com.WorkoutTrackerAnalyticsService.dto.WorkoutDTO;
import com.WorkoutTrackerAnalyticsService.repository.WorkoutRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkoutRepositoryImpl implements WorkoutRepository {

  private final RestTemplate restTemplate;

  @Value("${api.workout-service.base-url}")
  private String workoutServiceUrl;

  public WorkoutRepositoryImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<WorkoutDTO> getAllWorkoutsForUser(Long UserID) {
    String url = workoutServiceUrl + "/workouts/user/" + UserID;
    WorkoutDTO[] responseArray = restTemplate.getForObject(url, WorkoutDTO[].class);
    return Arrays.asList(responseArray);
  }

  @Override
  public WorkoutDTO getLastWorkoutForUser(Long UserID) {
    String url = workoutServiceUrl + "/workouts/last/" + UserID;
    WorkoutDTO responseArray = restTemplate.getForObject(url, WorkoutDTO.class);
    return responseArray;
  }

  @Override
  public List<WorkoutDTO> getWorkoutByName(Long userId, String workoutName) {
    String url = workoutServiceUrl + "/workouts/user/" + userId + "/" + workoutName;
    WorkoutDTO[] responseArray = restTemplate.getForObject(url, WorkoutDTO[].class);
    return Arrays.asList(responseArray);
  }

}