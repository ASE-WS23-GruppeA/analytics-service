package repository;

import com.WorkoutTrackerAnalyticsService.dto.WorkoutDTO;
import com.WorkoutTrackerAnalyticsService.repository.WorkoutRepository;
import com.WorkoutTrackerAnalyticsService.repository.impl.WorkoutRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WorkoutRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WorkoutRepositoryImpl workoutRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWorkoutsForUser() {
        Long userId = 1L;

        String expectedUrl = "https://api.workout-service.com/workouts/user/" + userId;

        WorkoutDTO[] responseArray = {
                createWorkoutDTO(1L, "Workout 1"),
                createWorkoutDTO(2L, "Workout 2"),
        };

        when(restTemplate.getForObject(expectedUrl, WorkoutDTO[].class)).thenReturn(responseArray);

        List<WorkoutDTO> expectedWorkouts = Arrays.asList(responseArray);

        List<WorkoutDTO> result = workoutRepository.getAllWorkoutsForUser(userId);

        verify(restTemplate, times(1)).getForObject(expectedUrl, WorkoutDTO[].class);

        assertEquals(expectedWorkouts, result);
    }


    @Test
    void testGetLastWorkoutForUser() {
        Long userId = 1L;
        String url = "https://api.workout-service.com/workouts/last/" + userId;

        WorkoutDTO expectedWorkout= new WorkoutDTO();;
        expectedWorkout.setUserID(userId);
        expectedWorkout.setWorkoutID(1L);

        when(restTemplate.getForObject(url, WorkoutDTO.class)).thenReturn(expectedWorkout);

        WorkoutDTO result = workoutRepository.getLastWorkoutForUser(userId);

        verify(restTemplate, times(1)).getForObject(url, WorkoutDTO.class);

        assertEquals(expectedWorkout, result);
    }



    private WorkoutDTO createWorkoutDTO(Long id, String name) {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setWorkoutID(id);
        workoutDTO.setWorkoutName(name);
        workoutDTO.setUserID(1L);
        workoutDTO.setCreatedDate("2023-01-01T12:00:00.000Z");
        return workoutDTO;
    }


}
