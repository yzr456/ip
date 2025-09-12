package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import mango.exception.MangoException;

/**
 * Task with a start and end date/time.
 */
public class Event extends Task {
    private static final String TYPE = "E";
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an {@code Event}.
     *
     * @param description event description
     * @param from start date/time in {@code yyyy-MM-dd HHmm} (e.g., {@code 2025-12-31 0900})
     * @param to end date/time in {@code yyyy-MM-dd HHmm} (e.g., {@code 2025-12-31 1100})
     * @throws IllegalArgumentException if either time cannot be parsed; message is
     *         {@link MangoException#ERR_BAD_DATE} and cause is a {@link DateTimeParseException}
     * @throws IllegalArgumentException if {@code to} is not strictly after {@code from};
     *         message is {@link MangoException#ERR_EVENT_RANGE}
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && to != null : "Event times must be non-null";
        try {
            this.from = LocalDateTime.parse(from, INPUT_FMT);
            this.to = LocalDateTime.parse(to, INPUT_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MangoException.ERR_BAD_DATE, e);
        }
        if (!this.to.isAfter(this.from)) {
            throw new IllegalArgumentException(MangoException.ERR_EVENT_RANGE);
        }
    }

    @Override
    protected String getTypeIdentifier() {
        return TYPE;
    }

    @Override
    public String toFileString() {
        assert this.from != null : "from must be parsed";
        assert this.to != null : "to must be parsed";
        return TYPE + FILE_SEP + statusFlag() + FILE_SEP + this.description
                + FILE_SEP + from.format(INPUT_FMT)
                + FILE_SEP + to.format(INPUT_FMT);
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + from.format(OUTPUT_FMT)
                + " to: " + to.format(OUTPUT_FMT) + ")";
    }
}
