package mango.task;

/**
 * Simplest type of task without a date or time.
 */
public class Todo extends Task {
    private static final String TYPE = "T";

    /**
     * Constructs a {@code Todo} task with the given description.
     *
     * @param description the task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypeIdentifier() {
        return TYPE;
    }

    @Override
    public String toFileString() {
        return TYPE + FILE_SEP + statusFlag() + FILE_SEP + this.description;
    }
}
