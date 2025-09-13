package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import mango.exception.MangoException;

/**
 * Task with a specific due date and time.
 */
public class Deadline extends Task {
    private static final String TYPE = "D";
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private final LocalDateTime by;

    /**
     * Creates a {@code Deadline}.
     *
     * @param description the task description.
     * @param by the due date/time in {@code yyyy-MM-dd HHmm} (e.g., {@code 2025-12-31 1800}).
     * @throws IllegalArgumentException if {@code by} cannot be parsed.
     */
    public Deadline(String description, String by) {
        super(description);
        assert by != null && !by.isBlank() : "Deadline 'by' must be non-empty";
        try {
            this.by = LocalDateTime.parse(by, INPUT_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MangoException.ERR_BAD_DATE, e);
        }
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
