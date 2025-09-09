package mango.task;

import java.io.IOException;

/**
 * Abstract base class representing a task.
 * Each task has a description and a completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * By default, the task is marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null : "Task description must be non-null";
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
    private String getStatusMark() {
        return (isDone ? "X" : " ");
    }

    /** @return one-letter task type identifier ("T", "D", or "E"). */
    protected abstract String getTypeIdentifier();

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
        assert line != null : "fromFileString expects non-null line";
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Serialized task must have at least 3 fields";
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
        if (done) {
            t.markAsDone();
        }
        assert t != null : "Deserializer must produce a task";
        return t;
    }

    @Override
    public String toString() {
        return "[" + this.getTypeIdentifier() + "][" + this.getStatusMark() + "] " + this.description;
    }
}
