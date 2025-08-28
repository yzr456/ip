package mango.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FMT);
        this.to = LocalDateTime.parse(to, INPUT_FMT);
    }

    protected String getTypeIcon() {
        return "E";
    }

    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(INPUT_FMT) + " | " + to.format(INPUT_FMT);
    }

    @Override public String toString() {
        return super.toString() + " (from: " + from.format(OUTPUT_FMT) + " to: " + to.format(OUTPUT_FMT) + ")";
    }
}

