
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/user/{userId}")
    public List<UserProgress> getUserProgress(@PathVariable Long userId) {
        return analyticsService.getUserProgress(userId);
    }
}
