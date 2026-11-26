import controller.ProjectController;
import persistence.ProjectRepository;
import view.ConsoleView;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ProjectRepository repository = new ProjectRepository(Path.of("projects.json"));
        
        new ProjectController(new ConsoleView(), repository).run();
    }
} 