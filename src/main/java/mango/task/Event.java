package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An {@code Event} task with a start and end date/time.
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
     * Constructs an {@code Event}.
     *
     * @param description description of the event
     * @param from start time string in format yyyy-MM-dd HHmm
     * @param to end time string in format yyyy-MM-dd HHmm
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FMT);
        this.to = LocalDateTime.parse(to, INPUT_FMT);
    }

    @Override
    protected String getTypeIdentifier() {
        return TYPE;
    }

    @Override
    public String toFileString() {
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
