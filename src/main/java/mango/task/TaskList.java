package mango.task;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TaskList} class represents a collection of tasks.
 * It provides methods to add, remove, view, and update tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} initialized with existing tasks.
     *
     * @param initial the initial list of tasks
     */
    public TaskList(List<Task> initial) {
        assert initial != null : "Initial list must be non-null";
        this.tasks = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count
     */
    public int size() {
        int s = this.tasks.size();
        assert s >= 0 : "Size cannot be negative";
        return s;
    }

    /**
     * Returns a view of the current task list.
     *
     * @return the list of tasks
     */
    public List<Task> view() {
        return this.tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t the task to add
     * @return the added task
     */
    public Task add(Task t) {
        assert t != null : "Cannot add null task";
        this.tasks.add(t);
        assert this.tasks.contains(t) : "Added task must be present";
        return t;
    }

    /**
     * Removes a task by index.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Index in range";
        Task r = this.tasks.remove(index);
        assert r != null : "Removed task should be non-null";
        return r;
    }

    /**
     * Marks a task as done.
     *
     * @param index the index of the task
     * @return the updated task
     */
    public Task mark(int index) {
        Task t = get(index);
        t.markAsDone();
        assert t.isDone : "Task should be marked done";
        return t;
    }

    /**
     * Marks a task as not done.
     *
     * @param index the index of the task
     * @return the updated task
     */
    public Task unmark(int index) {
        Task t = get(index);
        t.markAsNotDone();
        assert !t.isDone : "Task should be marked not done";
        return t;
    }

    /**
     * Retrieves the task at the specified index from the task list.
     *
     * @param index the zero-based index of the task to retrieve
     * @return the {@link Task} at the given index
     */
    public Task get(int index) {
        Task t = this.tasks.get(index);
        assert t != null : "Stored task should be non-null";
        return t;
    }

    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword the search keyword
     * @return list of matching tasks
     */
    public List<Task> find(String keyword) {
        assert keyword != null : "Keyword must be non-null (empty allowed)";

        List<Task> results = this.tasks.stream()
                .peek(t -> {
                    assert t != null : "Task list must not contain nulls";
                })
                .filter(t -> t.description.contains(keyword))
                .toList();
        assert results.stream().allMatch(t -> t.description.contains(keyword))
                : "Every result must contain the keyword";
        return results;
    }
}
