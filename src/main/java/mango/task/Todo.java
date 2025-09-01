package mango.task;

/**
 * A {@code Todo} is the simplest type of task, without a date or time.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypeIdentifier() {
        return "T";
    }

    @Override
    public String toFileString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }
}
