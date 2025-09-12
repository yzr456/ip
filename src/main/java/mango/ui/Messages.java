package mango.ui;

import java.util.List;

import mango.task.Task;

/**
 * Static helpers that format user-visible strings for MangoBot responses.
 */
public final class Messages {

    /**
     * Returns the welcome message.
     */
    public static String welcome() {
        return "Hey there! I’m MangoBot, your task-taming sidekick.\nWhat can I help you do today?";
    }

    /**
     * Returns the farewell message.
     */
    public static String bye() {
        return "MangoBot signing off — your tasks are safely stored!\n"
                + "May your day be as smooth as a ripe mango. See you soon!";
    }

    /**
     * Returns the invalid-input message.
     */
    public static String invalid() {
        return "Whoops! That doesn’t seem like a valid command.\n"
                + "Please double-check the command format and parameters, then try again.";
    }

    /**
     * Returns the storage-loading failure message.
     */
    public static String failedLoad() {
        return "Yikes! I couldn’t load your tasks from disk.\n"
                + "Don’t worry, your data should still be here, but please try again.";
    }

    /**
     * Returns the storage-saving failure message.
     */
    public static String failedSave() {
        return "Yikes! I couldn’t save your tasks to disk.\n"
                + "Don’t worry, your data should still be here, but please try again.";
    }

    /**
     * Returns an error message that echoes the provided text.
     *
     * @param message The error description.
     * @return The formatted error message.
     */
    public static String error(String message) {
        return "Error: " + message;
    }

    /**
     * Returns the confirmation message after adding a task.
     *
     * @param t The task added.
     * @param count The new total number of tasks.
     * @return The confirmation text.
     */
    public static String added(Task t, int count) {
        return "Sweet! I’ve added this to your task basket:\n   "
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
        return enumerateMassOpTasks("Poof! I’ve removed these tasks:\n", removedTasks)
                + "You’re now down to " + remainingCount + " task" + (remainingCount == 1 ? "" : "s") + ".";
    }

    /**
     * Returns the confirmation message after marking tasks done.
     *
     * @param tasks The tasks updated.
     * @return The confirmation text.
     */
    public static String marked(List<Task> tasks) {
        return enumerateMassOpTasks("Done and dusted! I’ve marked these as complete:\n", tasks);
    }

    /**
     * Returns the confirmation message after unmarking tasks.
     *
     * @param tasks The tasks updated.
     * @return The confirmation text.
     */
    public static String unmarked(List<Task> tasks) {
        return enumerateMassOpTasks("Back to the grind — I’ve marked these as not done yet:\n", tasks);
    }

    /**
     * Returns a numbered listing of tasks or a message if empty.
     *
     * @param tasks The tasks to list.
     * @return The formatted list text.
     */
    public static String list(List<Task> tasks) {
        return enumerateTasks("Here’s what’s on your plate today:",
                "Your task basket is empty — time to add some fresh mangoes!", tasks);
    }

    /**
     * Returns a numbered listing of matching tasks or a message if none match.
     *
     * @param tasks The matching tasks.
     * @return The formatted list text.
     */
    public static String found(List<Task> tasks) {
        return enumerateTasks(
                "Here are the tasks I found that match your search:",
                "Hmm... no matching tasks here. Try a different keyword!", tasks);
    }

    private static String enumerateMassOpTasks(String header, List<Task> tasks) {
        return header + tasks.stream()
                .map(t -> "   " + t)
                .reduce((line1, line2) -> line1 + "\n" + line2)
                .orElse("");
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
