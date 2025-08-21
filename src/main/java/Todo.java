public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    protected String getTypeIcon() {
        return "T";
    }
}
