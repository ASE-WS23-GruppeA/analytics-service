
@Service
public class AnalyticsService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    public List<UserProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId);
    }
}
