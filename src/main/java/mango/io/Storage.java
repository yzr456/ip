package mango.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import mango.task.Task;

/**
 * The {@code Storage} class is responsible for saving and loading tasks
 * from a specified file path. It ensures that the storage file and
 * its parent directories exist.
 */
public class Storage {
    private final Path filePath;

    /**
     * Constructs a {@code Storage} object for the given file path.
     * The file and directories will be created if they do not exist.
     *
     * @param filePath the path of the storage file
     * @throws IOException if an error occurs during file initialization
     */
    public Storage(String filePath) throws IOException {
        this.filePath = Paths.get(filePath);
        this.init();
    }

    /**
     * Ensures the storage file and parent directories exist.
     *
     * @throws IOException if the file or directories cannot be created
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
     * @return a list of {@code Task} objects loaded from the file
     * @throws IOException if an error occurs while reading the file
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        return Files.lines(filePath)
                .map(line -> {
                    try {
                        return Task.fromFileString(line);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .toList();
    }

    /**
     * Saves the given tasks to the storage file.
     *
     * @param tasks the list of tasks to save
     * @throws IOException if an error occurs while writing to the file
     */
    public void save(List<Task> tasks) throws IOException {
        Files.write(filePath,
                tasks.stream()
                        .map(t -> t.toFileString())
                        .toList()
        );
    }
}
