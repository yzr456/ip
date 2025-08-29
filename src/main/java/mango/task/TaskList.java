package mango.task;

import java.util.ArrayList;
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

    /**
     * Retrieves the task at the specified index from the task list.
     *
     * @param index the zero-based index of the task to retrieve
     * @return the {@link Task} at the given index
     */
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

    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword the search keyword
     * @return list of matching tasks
     */
    public List<Task> find(String keyword) {
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.description.contains(keyword)) {
                results.add(t);
            }
        }
        return results;
    }
}
