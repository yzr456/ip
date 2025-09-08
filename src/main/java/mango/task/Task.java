package mango.task;

import java.io.IOException;

/**
 * Abstract base class representing a task.
 * Each task has a description and a completion status.
 */
public abstract class Task {
    protected static final String FILE_SEP = " | ";
    protected static final String FLAG_DONE = "1";
    protected static final String FLAG_NOT_DONE = "0";
    private static final String DELIMITER = " \\| ";

    private static final String STATUS_DONE = "X";
    private static final String STATUS_NOT_DONE = " ";

    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";

    private static final int TYPE_INDEX = 0;
    private static final int FLAG_INDEX = 1;
    private static final int DESC_INDEX = 2;
    private static final int DEADLINE_BY_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * By default, the task is marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Deserializes a {@code Task} from a line in storage file.
     *
     * @param line the serialized task string
     * @return the corresponding {@code Task}
     * @throws IOException if the type is unknown
     */
    public static Task fromFileString(String line) throws IOException {
        String[] parts = line.split(DELIMITER);
        String type = parts[TYPE_INDEX];
        boolean isDone = FLAG_DONE.equals(parts[FLAG_INDEX]);
        String desc = parts[DESC_INDEX];

        Task task = constructTaskFromType(type, desc, parts);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Creates the appropriate {@code Task} based on the type identifier.
     */
    private static Task constructTaskFromType(String type, String desc, String[] parts) throws IOException {
        return switch (type) {
            case TODO_TYPE -> new Todo(desc);
            case DEADLINE_TYPE -> new Deadline(desc, parts[DEADLINE_BY_INDEX]);
            case EVENT_TYPE -> new Event(desc, parts[EVENT_FROM_INDEX], parts[EVENT_TO_INDEX]);
            default -> throw new IOException("Unknown task type: " + type);
        };
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** @return serialized string representation for saving to file. */
    public abstract String toFileString();

    protected String statusFlag() {
        return isDone ? FLAG_DONE : FLAG_NOT_DONE;
    }

    /** @return one-letter task type identifier ("T", "D", or "E"). */
    protected abstract String getTypeIdentifier();


    /** @return "X" if task is done, otherwise a space. */
    private String getStatusMark() {
        return isDone ? STATUS_DONE : STATUS_NOT_DONE;
    }

    @Override
    public String toString() {
        return "[" + this.getTypeIdentifier() + "][" + this.getStatusMark() + "] " + this.description;
    }
}
