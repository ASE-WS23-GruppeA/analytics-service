package WorkoutTrackerAnalyticsService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.WorkoutTrackerAnalyticsService.dto.ExerciseDTO;
import com.WorkoutTrackerAnalyticsService.dto.WorkoutDTO;
import com.WorkoutTrackerAnalyticsService.dto.WorkoutSetDTO;
import com.WorkoutTrackerAnalyticsService.repository.ExerciseRepository;
import com.WorkoutTrackerAnalyticsService.repository.WorkoutRepository;
import com.WorkoutTrackerAnalyticsService.service.impl.AnalyticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AnalyticsServiceImplTest {

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
        assertEquals(1, result.get("gymVisits"));
        assertEquals(createExpectedTrainingInfo(), result.get("trainingInfo"));
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
    private Map<LocalDate, List<Map<String, Object>>> createExpectedTrainingInfo() {
        Map<LocalDate, List<Map<String, Object>>> trainingInfoMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDate workoutDate = LocalDate.parse("2023-01-01T12:00:00.000Z", formatter);

        Map<String, Object> exerciseInfo = new HashMap<>();
        exerciseInfo.put("exercise", "Sit-ups");
        exerciseInfo.put("sets", 1L);
        exerciseInfo.put("reps", 10);

        List<Map<String, Object>> exerciseList = new ArrayList<>();
        exerciseList.add(exerciseInfo);

        trainingInfoMap.put(workoutDate, exerciseList);

        return trainingInfoMap;
    }


    //Integration tests:
    @Test
    public void IntegrationTestGetWeightProgressForExercise() {
        // Mock data
        List<WorkoutDTO> workouts = Arrays.asList(
                createWorkoutDTO(1L, "2023-01-01", createWorkoutSetDTO(1L, 10L)),
                createWorkoutDTO(1L, "2023-01-02", createWorkoutSetDTO(2L, 15L))
        );
        List<ExerciseDTO> exercises = Collections.singletonList(createExerciseDTO(1L, "Sit-ups"));

        // Mock repository calls
        Mockito.when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(workouts);
        Mockito.when(exerciseRepository.getAllExercises()).thenReturn(exercises);

        // Perform the test
        Map<String, Double> result = analyticsService.getWeightProgressForExercise(1L, "Sit-ups", LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-02"));

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(10.0, result.get("2023-01-01"));
        assertEquals(15.0, result.get("2023-01-02"));
    }

    private WorkoutDTO createWorkoutDTO(long userId, String createdDate, WorkoutSetDTO... sets) {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setUserID(userId);
        workoutDTO.setCreatedDate(createdDate);
        workoutDTO.setWorkoutSets(Arrays.asList(sets));
        return workoutDTO;
    }

    private WorkoutSetDTO createWorkoutSetDTO(long exerciseId, Long weights) {
        WorkoutSetDTO setDTO = new WorkoutSetDTO();
        setDTO.setExerciseID(exerciseId);
        //setDTO.setExerciseName("Sit-ups");
        setDTO.setWeights(weights);
        return setDTO;
    }

    private ExerciseDTO createExerciseDTO(long exerciseId, String exerciseName) {
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setExerciseID(exerciseId);
        exerciseDTO.setExerciseName(exerciseName);
        return exerciseDTO;
    }

}
