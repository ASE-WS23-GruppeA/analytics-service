package com.WorkoutTrackerAnalyticsServiceTest.repository;

import com.WorkoutTrackerAnalyticsService.model.WorkoutProgress;
import com.WorkoutTrackerAnalyticsService.repository.UserProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan(basePackages = "com.WorkoutTrackerAnalyticsService")
@SpringBootApplication
class UserProgressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserProgressRepository userProgressRepository;

    @ParameterizedTest
    @CsvSource({
            "workoutID,1,user123,user123",
            "muscleGroup,Legs,user123,user123",
            "exerciseID,1,user123,user123",
            "userID,user123,,user123",
            "workoutSetsID,1,user123,user123",
            "muscleGroupAndExerciseName,Chest,Bench Press,user123",
            "exerciseNameAndUserID,Squat,user123,user123"
    })

    void testFindByParameter(String parameter, String value1, String value2, String expectedUserId) {
        // Insert test data into the database based on the parameters
        WorkoutProgress workoutProgress = new WorkoutProgress();
        switch (parameter) {
            case "workoutID":
                workoutProgress.setWorkoutID(Long.parseLong(value1));
                break;
            case "muscleGroup":
                workoutProgress.setMuscleGroup(value1);
                break;

        }
        workoutProgress.setUserId(expectedUserId);
        workoutProgress.setStartTime(LocalDateTime.now());
        entityManager.persist(workoutProgress);

        // Call repository method based on the parameter
        List<WorkoutProgress> result = switch (parameter) {
            case "workoutID" -> userProgressRepository.findByWorkoutID(Long.parseLong(value1));
            case "muscleGroup" -> userProgressRepository.findByMuscleGroup(value1);
            case "exerciseID" -> userProgressRepository.findByExerciseID(Long.parseLong(value1));
            case "userID" -> userProgressRepository.findByUserID(value1);
            case "workoutSetsID" -> userProgressRepository.findByWorkoutSetsID(Long.parseLong(value1));
            case "muscleGroupAndExerciseName" -> userProgressRepository.findByMuscleGroupAndExerciseName(value1, value2);
            case "exerciseNameAndUserID" -> userProgressRepository.findByExerciseNameAndUserID(value1, value2);
            default -> throw new IllegalArgumentException("Unexpected value: " + parameter);
        };

        // Assert the results
        assertEquals(1, result.size());
        assertEquals(expectedUserId, result.get(0).getUserID());
    }
}
