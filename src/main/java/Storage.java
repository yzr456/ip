import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void init() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        for (String line : Files.readAllLines(filePath)) {
            tasks.add(Task.fromFileString(line));
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toFileString());
        }
        Files.write(filePath, lines);
    }
}
