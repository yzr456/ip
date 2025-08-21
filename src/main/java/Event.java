public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    protected String getTypeIcon() {
        return "E";
    }

    @Override public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

