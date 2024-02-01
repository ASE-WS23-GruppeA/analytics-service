package repository;

import com.WorkoutTrackerAnalyticsService.dto.ExerciseDTO;
import com.WorkoutTrackerAnalyticsService.repository.impl.ExerciseRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExerciseRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExerciseRepositoryImpl exerciseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllExercises() {

        ExerciseDTO[] mockResponseArray = {new ExerciseDTO(), new ExerciseDTO()};
        List<ExerciseDTO> expectedExercises = Arrays.asList(mockResponseArray);
        when(restTemplate.getForObject(anyString(), eq(ExerciseDTO[].class))).thenReturn(mockResponseArray);


        List<ExerciseDTO> result = exerciseRepository.getAllExercises();

        assertEquals(expectedExercises, result);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(ExerciseDTO[].class));
    }

    @Test
    void testGetExerciseById() {
        Long exerciseId = 1L;
        ExerciseDTO expectedExercise = new ExerciseDTO();
        when(restTemplate.getForObject(anyString(), eq(ExerciseDTO.class))).thenReturn(expectedExercise);


        ExerciseDTO result = exerciseRepository.getExerciseById(exerciseId);


        assertEquals(expectedExercise, result);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(ExerciseDTO.class));
    }
}
