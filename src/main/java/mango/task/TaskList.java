package mango.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import mango.exception.MangoException;

/**
 * Mutable collection of {@link Task}s with operations to add, remove, update, and query.
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
     * @param initial The initial list of tasks.
     */
    public TaskList(List<Task> initial) {
        assert initial != null : "Initial list must be non-null";
        this.tasks = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The task count.
     */
    public int size() {
        int s = this.tasks.size();
        assert s >= 0 : "Size cannot be negative";
        return s;
    }

    /**
     * Returns a view of the current task list.
     *
     * @return The list of tasks.
     */
    public List<Task> view() {
        return this.tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t The task to add.
     * @return The added task.
     */
    public Task add(Task t) throws MangoException {
        assert t != null : "Cannot add null task";
        if (this.tasks.contains(t)) {
            throw new MangoException(MangoException.ERR_DUPLICATE_TASK);
        }
        this.tasks.add(t);
        assert this.tasks.contains(t) : "Added task must be present";
        return t;
    }

    /**
     * Removes tasks at the specified indices.
     *
     * @param indices List of zero-based indices (may be unsorted).
     * @return The list of removed tasks.
     */
    public List<Task> remove(List<Integer> indices) {
        assert indices != null && !indices.isEmpty() : "Indices must not be null or empty";
        return indices.stream()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .mapToObj(i -> tasks.remove(i))
                .toList();
    }

    /**
     * Marks tasks as done at the specified indices.
     *
     * @param indices List of zero-based indices.
     * @return The list of updated tasks.
     */
    public List<Task> mark(List<Integer> indices) {
        assert indices != null && !indices.isEmpty() : "Indices must not be null or empty";
        return indices.stream()
                .map(i -> tasks.get(i))
                .peek(Task::markAsDone)
                .toList();
    }

    /**
     * Marks tasks as not done at the specified indices.
     *
     * @param indices List of zero-based indices.
     * @return The list of updated tasks.
     */
    public List<Task> unmark(List<Integer> indices) {
        assert indices != null && !indices.isEmpty() : "Indices must not be null or empty";
        return indices.stream()
                .map(i -> tasks.get(i))
                .peek(Task::markAsNotDone)
                .toList();
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The task at the given index.
     */
    public Task get(int index) {
        Task t = this.tasks.get(index);
        assert t != null : "Stored task should be non-null";
        return t;
    }

    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword The search keyword.
     * @return The list of matching tasks.
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
