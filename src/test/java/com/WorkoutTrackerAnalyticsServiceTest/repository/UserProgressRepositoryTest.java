package com.WorkoutTrackerAnalyticsServiceTest.repository;
import com.WorkoutTrackerAnalyticsService.repository.UserProgressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan(basePackages = "com.WorkoutTrackerAnalyticsService")
@SpringBootApplication
class UserProgressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Test
    void testFindByWorkoutID() {
        // Implement your test logic here
        // Use entityManager to insert test data into the database
        // Call repository methods and assert the results
    }

    // Add similar tests for other repository methods
}
