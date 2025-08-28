package mango.task;

import java.io.IOException;

/**
 * Abstract base class representing a task.
 * Each task has a description and a completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** @return "X" if task is done, otherwise a space. */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** @return one-letter task type identifier ("T", "D", or "E"). */
    protected abstract String getTypeIcon();

    /** @return serialized string representation for saving to file. */
    public abstract String toFileString();

    /**
     * Deserializes a {@code Task} from a line in storage file.
     *
     * @param line the serialized task string
     * @return the corresponding {@code Task}
     * @throws IOException if the type is unknown
     */
    public static Task fromFileString(String line) throws IOException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        Task t;
        switch (type) {
        case "T" -> t = new Todo(desc);
        case "D" -> t = new Deadline(desc, parts[3]);
        case "E" -> t = new Event(desc, parts[3], parts[4]);
        default -> throw new IOException("Unknown task type: " + type);
        }
        if (done) t.markAsDone();
        return t;
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
    }
}
