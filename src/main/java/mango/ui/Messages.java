package mango.ui;

import java.util.List;

import mango.task.Task;

/**
 * Static helpers that format user-visible strings for MangoBot responses.
 */
public final class Messages {

    private Messages() { }

    /**
     * Returns the welcome message.
     */
    public static String welcome() {
        return "Hello! I'm MangoBot\n What can I do for you?";
    }

    /**
     * Returns the farewell message.
     */
    public static String bye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns the invalid-input message.
     */
    public static String invalid() {
        return "Invalid input.";
    }

    /**
     * Returns the storage-failure message.
     */
    public static String failedSave() {
        return "Failed to save tasks to disk.";
    }

    /**
     * Returns an error message that echoes the provided text.
     *
     * @param message The error description.
     * @return The formatted error message.
     */
    public static String error(String message) {
        return message;
    }

    /**
     * Returns the confirmation message after adding a task.
     *
     * @param t The task added.
     * @param count The new total number of tasks.
     * @return The confirmation text.
     */
    public static String added(Task t, int count) {
        return "Got it. I've added this task:\n   "
                + t + "\nNow you have " + count + " tasks in the list.";
    }

    /**
     * Returns the confirmation message after removing tasks.
     *
     * @param removedTasks The tasks removed.
     * @param remainingCount The number of tasks left.
     * @return The confirmation text.
     */
    public static String removed(List<Task> removedTasks, int remainingCount) {
        return enumerateMassOpTasks("Noted. I've removed these tasks:\n",
                removedTasks) + "Now you have " + remainingCount + " tasks in the list.";
    }

    /**
     * Returns the confirmation message after marking tasks done.
     *
     * @param tasks The tasks updated.
     * @return The confirmation text.
     */
    public static String marked(List<Task> tasks) {
        return enumerateMassOpTasks("Nice! I've marked this task as done:\n", tasks);
    }

    /**
     * Returns the confirmation message after unmarking tasks.
     *
     * @param tasks The tasks updated.
     * @return The confirmation text.
     */
    public static String unmarked(List<Task> tasks) {
        return enumerateMassOpTasks("OK, I've marked this task as not done yet:\n", tasks);
    }

    /**
     * Returns a numbered listing of tasks or a message if empty.
     *
     * @param tasks The tasks to list.
     * @return The formatted list text.
     */
    public static String list(List<Task> tasks) {
        return enumerateTasks("Here are the tasks in your list:", "No tasks have been added yet.", tasks);
    }

    /**
     * Returns a numbered listing of matching tasks or a message if none match.
     *
     * @param tasks The matching tasks.
     * @return The formatted list text.
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
