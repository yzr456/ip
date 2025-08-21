public class Deadline extends Task {
    private final String by;
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    protected String getTypeIcon() {
        return "D";
    }
    @Override public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}

