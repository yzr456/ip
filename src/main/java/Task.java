import java.io.IOException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    protected abstract String getTypeIcon();

    public abstract String toFileString();

    public static Task fromFileString(String line) throws IOException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        Task t;
        switch (type) {
        case "T" -> t = new Todo(desc);
        case "D" -> t = new Deadline(desc, parts[3]);
        case "E" -> t = new Event(desc, parts[3], parts[4]);
        default -> throw new IOException("Unknown task type: " + type);
        }
        if (done) {
            t.markAsDone();
        }
        return t;
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
    }
}
