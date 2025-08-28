package mango.task;

/**
 * A {@code Todo} is the simplest type of task, without a date or time.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    protected String getTypeIcon() {
        return "T";
    }

    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
