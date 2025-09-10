package mango.ui;

import java.util.List;

import mango.task.Task;

/**
 * Static helpers that format strings for MangoBot responses.
 */
public final class Messages {

    /** @return the welcome message. */
    public static String welcome() {
        return "Hello! I'm MangoBot\n What can I do for you?";
    }

    /** @return the farewell message. */
    public static String bye() {
        return "Bye. Hope to see you again soon!";
    }

    /** @return the invalid-input message. */
    public static String invalid() {
        return "Invalid input.";
    }

    /** @return the storage-failure message. */
    public static String failedSave() {
        return "Failed to save tasks to disk.";
    }

    /**
     * Returns an error message that simply echoes the provided text.
     *
     * @param message the error description
     * @return the formatted error message
     */
    public static String error(String message) {
        return message;
    }

    /**
     * Returns the confirmation message after adding a task.
     *
     * @param t the task added
     * @param count the new total number of tasks
     * @return the confirmation text
     */
    public static String added(Task t, int count) {
        return "Got it. I've added this task:\n   "
                + t + "\nNow you have " + count + " tasks in the list.";
    }

    /**
     * Returns the confirmation message after removing tasks.
     *
     * @param removedTasks the tasks removed
     * @param remainingCount the number of tasks left
     * @return the confirmation text
     */
    public static String removed(List<Task> removedTasks, int remainingCount) {
        return enumerateMassOpTasks("Noted. I've removed these tasks:\n",
                removedTasks) + "Now you have " + remainingCount + " tasks in the list.";
    }

    /**
     * Returns the confirmation message after marking tasks done.
     *
     * @param markedTasks the tasks updated
     * @return the confirmation text
     */
    public static String marked(List<Task> markedTasks) {
        return enumerateMassOpTasks("Nice! I've marked this task as done:\n", markedTasks);
    }

    /**
     * Returns the confirmation message after unmarking tasks.
     *
     * @param unmarkedTasks the tasks updated
     * @return the confirmation text
     */
    public static String unmarked(List<Task> unmarkedTasks) {
        return enumerateMassOpTasks("OK, I've marked this task as not done yet:\n", unmarkedTasks);
    }

    /**
     * Returns a numbered listing of tasks or a message if empty.
     *
     * @param tasks the tasks to list
     * @return the formatted list text
     */
    public static String list(List<Task> tasks) {
        return enumerateTasks("Here are the tasks in your list:", "No tasks have been added yet.", tasks);
    }

    /**
     * Returns a numbered listing of matching tasks or a message if none match.
     *
     * @param tasks the matching tasks
     * @return the formatted list text
     */
    public static String found(List<Task> tasks) {
        return enumerateTasks("Here are the matching tasks in your list:", "No matching tasks found.", tasks);
    }

    private static String enumerateMassOpTasks(String header, List<Task> tasks) {
        return header + tasks.stream()
                .map(t -> "   " + t + "\n")
                .reduce("", (acc, line) -> acc + line);
    }

    private static String enumerateTasks(String header, String textToShow, List<Task> tasks) {
        if (tasks.isEmpty()) {
            return header + "\n" + textToShow;
        }
        return header + tasks.stream()
                .map(task -> (tasks.indexOf(task) + 1) + "." + task)
                .reduce("", (acc, line) -> acc + "\n" + line);
    }
}
