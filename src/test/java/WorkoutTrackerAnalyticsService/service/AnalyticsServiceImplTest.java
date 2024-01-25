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

    // Unit Test
    @Test
    void testGetWeightProgressForExercise() {
        when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(Collections.emptyList());
        when(exerciseRepository.getAllExercises()).thenReturn(Collections.emptyList());
        Map<String, Double> weightProgressMap = analyticsService.getWeightProgressForExercise(1L, "Sit-ups", LocalDate.now(), LocalDate.now());
        assertEquals(Collections.emptyMap(), weightProgressMap);
    }



    @Test
    void testGetUserTrainingInfo() {

        List<WorkoutDTO> userWorkouts = Collections.singletonList(createMockWorkout());
        when(workoutRepository.getAllWorkoutsForUser(anyLong())).thenReturn(userWorkouts);
        when(exerciseRepository.getExerciseById(anyLong())).thenReturn(createMockExercise());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDate startDate = LocalDate.parse("2024-01-23 17:24:52.571", formatter);
        LocalDate endDate = LocalDate.parse("2024-01-23 20:30:58.673", formatter);


        Map<String, Object> userTrainingInfo = analyticsService.getUserTrainingInfo(1L,startDate , endDate);

        assertEquals(createExpectedTrainingInfo(), userTrainingInfo);
    }

    // Helper methods to create mock objects and expected results...

    private WorkoutDTO createMockWorkout() {
        WorkoutDTO workout = new WorkoutDTO();
        workout.setUserID(1L);
        workout.setCreatedDate(LocalDate.now().toString());

        WorkoutSetDTO workoutSet = new WorkoutSetDTO();
        workoutSet.setExerciseID(1L);
        workoutSet.setWorkoutSetsID(3L);
        workoutSet.setReps(10L);

        workout.setWorkoutSets(Collections.singletonList(workoutSet));

        return workout;
    }

    private ExerciseDTO createMockExercise() {
        ExerciseDTO exercise = new ExerciseDTO();
        exercise.setExerciseID(1L);
        exercise.setExerciseName("Sit-ups");

        return exercise;
    }

    private Map<String, Object> createExpectedTrainingInfo() {
        Map<String, Object> exerciseInfo = new HashMap<>();
        exerciseInfo.put("exercise", "Sit-ups");
        exerciseInfo.put("sets", 3);
        exerciseInfo.put("reps", 10);

        Map<LocalDate, List<Map<String, Object>>> expectedTrainingInfoMap = new HashMap<>();
        expectedTrainingInfoMap.put(LocalDate.now(), Collections.singletonList(exerciseInfo));

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("gymVisits", 1);
        expectedResult.put("trainingInfo", expectedTrainingInfoMap);

        return expectedResult;
    }


}
