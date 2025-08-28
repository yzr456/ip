import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public int size() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public List<Task> view() {
       return this.tasks;
    }

    public Task add(Task t) {
        this.tasks.add(t);
        return t;
    }

    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public Task mark(int index) {
        Task t = get(index);
        t.markAsDone();
        return t;
    }

    public Task unmark(int index) {
        Task t = get(index);
        t.markAsNotDone();
        return t;
    }
}
