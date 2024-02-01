package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.WorkoutTrackerAnalyticsService.dto.ExerciseDTO;
import com.WorkoutTrackerAnalyticsService.dto.WorkoutDTO;
import com.WorkoutTrackerAnalyticsService.dto.WorkoutSetDTO;
import com.WorkoutTrackerAnalyticsService.repository.ExerciseRepository;
import com.WorkoutTrackerAnalyticsService.repository.WorkoutRepository;
import com.WorkoutTrackerAnalyticsService.service.impl.AnalyticsServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
class AnalyticsServiceImplTest {

    //Unit tests
    @Nested
    class AnalyticsServiceImplUnitTest {
        private AnalyticsServiceImpl analyticsService;
        private WorkoutRepository workoutRepository;
        private ExerciseRepository exerciseRepository;

        @BeforeEach
        void setUp() {
            workoutRepository = mock(WorkoutRepository.class);
            exerciseRepository = mock(ExerciseRepository.class);
            analyticsService = new AnalyticsServiceImpl(workoutRepository, exerciseRepository);
        }

        //Unit Tests:
        @Test
        void UnitTestGetWeightProgressForExercise() {
            when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(Collections.emptyList());
            when(exerciseRepository.getAllExercises()).thenReturn(Collections.emptyList());
            Map<String, Double> weightProgressMap = analyticsService.getWeightProgressForExercise(1L, "Sit-ups", LocalDate.now(), LocalDate.now());
            assertEquals(Collections.emptyMap(), weightProgressMap);
        }

        @Test
        void UnitTestGetAverageWeightProgressByMuscleGroup() {
            // Mock data and expectations
            List<WorkoutDTO> userWorkouts = Collections.singletonList(createMockWorkout());

            when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(userWorkouts);
            when(exerciseRepository.getExerciseById(anyLong())).thenReturn(createMockExercise());

            // Perform the test
            Map<String, Object> result = analyticsService.getAverageWeightProgressByMuscleGroup(
                    1L, "Legs", LocalDate.now(), LocalDate.now());

            // Assert the result
            assertNotNull(result);
            assertEquals(0.0, result.get("averageWeightProgress"));
            assertEquals("Legs", result.get("MuscleGroup"));
        }

        @Test
        void UnitTestGetUserTrainingInfo() {
            // Mock data and expectations
            List<WorkoutDTO> userWorkouts = createMockWorkouts();
            when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(userWorkouts);
            when(exerciseRepository.getExerciseById(anyLong())).thenReturn(createMockExercise());

            // Perform the test
            Map<String, Object> result = analyticsService.getUserTrainingInfo(
                    1L, LocalDate.now(), LocalDate.now());

            // Assert the result
            assertEquals(0, result.get("gymVisits"));

            // Verify that the workoutRepository method was called
            verify(workoutRepository, times(1)).getAllWorkoutsForUser(1L);
        }


        private WorkoutDTO createMockWorkout() {
            WorkoutDTO workout = new WorkoutDTO();
            workout.setUserID(1L);
            workout.setCreatedDate("2023-01-01T12:00:00.000Z");
            WorkoutSetDTO set = new WorkoutSetDTO();
            set.setExerciseID(1L);
            set.setWeights(50L);
            workout.setWorkoutSets(Collections.singletonList(set));
            return workout;
        }

        private ExerciseDTO createMockExercise() {
            ExerciseDTO exercise = new ExerciseDTO();
            exercise.setExerciseID(1L);
            exercise.setExerciseName("Sit-ups");
            exercise.setMuscleGroup("Legs");
            return exercise;
        }

        private List<WorkoutDTO> createMockWorkouts() {
            List<WorkoutDTO> workouts = new ArrayList<>();

            WorkoutDTO workout1 = new WorkoutDTO();
            workout1.setUserID(1L);
            workout1.setCreatedDate("2023-01-01T12:00:00.000Z");
            workout1.setWorkoutSets(Collections.singletonList(createMockSet()));
            workouts.add(workout1);

            WorkoutDTO workout2 = new WorkoutDTO();
            workout2.setUserID(1L);
            workout2.setCreatedDate("2023-01-02T12:00:00.000Z");
            workout2.setWorkoutSets(Collections.singletonList(createMockSet()));
            workouts.add(workout2);

            return workouts;
        }

        private WorkoutSetDTO createMockSet() {
            WorkoutSetDTO set = new WorkoutSetDTO();
            set.setExerciseID(1L);
            set.setWorkoutSetsID(1L);
            set.setReps(10L);
            return set;
        }
    }


    //Integration tests:
    @Nested
    @AutoConfigureMockMvc
    class AnalyticsServiceImplIntegrationTest
    {
        private AnalyticsServiceImpl analyticsService;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private WorkoutRepository workoutRepository;

        @Autowired
        private ExerciseRepository exerciseRepository;

        @Test
        void testGetWeightProgressForExercise() throws Exception {
            // Вы можете использовать тестовые данные из файла data.sql
            Long userId = 1L;
            String exerciseName = "Sit-ups";
            LocalDate startDate = LocalDate.parse("2023-01-01");
            LocalDate endDate = LocalDate.parse("2023-12-31");

            // Выполните запрос на ваш реальный API-конечный пункт
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/weight-progress/{userId}/{exerciseName}", userId, exerciseName)
                            .param("startDate", startDate.toString())
                            .param("endDate", endDate.toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            // Проверьте статус ответа
            assertEquals(200, mvcResult.getResponse().getStatus());

            String responseJson = ((MvcResult) mvcResult).getResponse().getContentAsString();
            Map<String, Double> weightProgress = new ObjectMapper().readValue(responseJson, new TypeReference<>() {});

            assertEquals(2, weightProgress.size());
            assertTrue(weightProgress.containsKey("2023-01-01"));
            assertTrue(weightProgress.containsKey("2023-01-02"));

            Map<String, Double> weightProgressFromService = analyticsService.getWeightProgressForExercise(userId, exerciseName, startDate, endDate);
            assertEquals(weightProgress, weightProgressFromService);
        }
    }



}
