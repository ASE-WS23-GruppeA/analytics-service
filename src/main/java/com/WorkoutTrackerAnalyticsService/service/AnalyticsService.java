package com.WorkoutTrackerAnalyticsService.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorkoutTrackerAnalyticsService.repository.*;
import com.WorkoutTrackerAnalyticsService.model.UserProgress;

import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    public List<UserProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId);
    }
}
