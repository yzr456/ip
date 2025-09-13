package mango.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import mango.task.Task;

/**
 * Persists tasks to disk and restores them from a file.
 *
 * <p>Ensures that the storage file and its parent directories exist.</p>
 */
public class Storage {
    private final Path filePath;

    /**
     * Constructs a {@code Storage} object for the given file path.
     *
     * <p>The file and directories are created if they do not exist.</p>
     *
     * @param filePath the path of the storage file.
     * @throws IOException if an error occurs during file initialization.
     */
    public Storage(String filePath) throws IOException {
        this.filePath = Paths.get(filePath);
        this.init();
        assert Files.exists(this.filePath) : "Storage file must exist after init()";
    }

    /**
     * Ensures the storage file and parent directories exist.
     *
     * @throws IOException if the file or directories cannot be created.
     */
    private void init() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return a list of tasks loaded from the file.
     * @throws IOException if an error occurs while reading the file.
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        for (String line : Files.readAllLines(filePath)) {
            assert line != null : "Read line must be non-null";
            tasks.add(Task.fromFileString(line));
        }
        assert tasks != null : "Never return null";
        return tasks;
    }

    /**
     * Saves the given tasks to the storage file.
     *
     * @param tasks the list of tasks to save.
     * @throws IOException if an error occurs while writing to the file.
     */
    public void save(List<Task> tasks) throws IOException {
        assert tasks != null : "Null list should not be saved";
        Files.write(filePath,
                tasks.stream()
                        .peek(t -> {
                            assert t != null : "Cannot save null task";
                        })
                        .map(t -> t.toFileString())
                        .toList()
        );
        assert Files.exists(filePath) : "File must exist after write";
    }
}
