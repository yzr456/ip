package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A {@code Deadline} task that has a specific due date/time.
 */
public class Deadline extends Task {
    private static final String TYPE = "D";
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private final LocalDateTime by;

    /**
     * Constructs a {@code Deadline} task.
     *
     * @param description description of the task
     * @param by date and time string in format yyyy-MM-dd HHmm
     */
    public Deadline(String description, String by) {
        super(description);
        assert by != null && !by.isBlank() : "Deadline 'by' must be non-empty";
        this.by = LocalDateTime.parse(by, INPUT_FMT);
    }

    @Override
    protected String getTypeIdentifier() {
        return TYPE;
    }

    @Override
    public String toFileString() {
        assert this.by != null : "by must be parsed";
        return TYPE + FILE_SEP + statusFlag() + FILE_SEP
                + this.description + FILE_SEP + by.format(INPUT_FMT);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(OUTPUT_FMT) + ")";
    }
}
