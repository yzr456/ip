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
        this.tasks = new ArrayList<>(initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count
     */
    public int size() {
        return this.tasks.size();
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
        this.tasks.add(t);
        return t;
    }

    /**
     * Removes a task by index.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task remove(int index) {
        return this.tasks.remove(index);
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
        return t;
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }
}
