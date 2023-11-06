package com.WorkoutTrackerAnalyticsService.service;
import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;

import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    public List<WorkoutProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId);
    }
}
