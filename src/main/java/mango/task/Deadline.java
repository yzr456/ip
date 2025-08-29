package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A {@code Deadline} task that has a specific due date/time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructs a {@code Deadline} task.
     *
     * @param description description of the task
     * @param by date and time string in format yyyy-MM-dd HHmm
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FMT);
    }

    @Override
    protected String getTypeIcon() {
        return "D";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FMT);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(OUTPUT_FMT) + ")";
    }
}
