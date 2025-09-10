package mango.ui;

import java.util.List;

import mango.task.Task;

public final class Messages {

    public static String welcome() {
        return "Hello! I'm MangoBot\n What can I do for you?";
    }

    public static String bye() {
        return "Bye. Hope to see you again soon!";
    }

    public static String invalid() {
        return "Invalid input.";
    }

    public static String failedSave() {
        return "Failed to save tasks to disk.";
    }

    public static String error(String message) {
        return message;
    }

    public static String added(Task t, int count) {
        return "Got it. I've added this task:\n   "
                + t + "\nNow you have " + count + " tasks in the list.";
    }

    public static String removed(List<Task> removedTasks, int remainingCount) {
        return enumerateMassOpTasks("Noted. I've removed these tasks:\n",
                removedTasks) + "Now you have " + remainingCount + " tasks in the list.";
    }

    public static String marked(List<Task> tasks) {
        return enumerateMassOpTasks("Nice! I've marked this task as done:\n", tasks);
    }

    public static String unmarked(List<Task> tasks) {
        return enumerateMassOpTasks("OK, I've marked this task as not done yet:\n", tasks);
    }

    public static String list(List<Task> tasks) {
        return enumerateTasks("Here are the tasks in your list:", "No tasks have been added yet.", tasks);
    }

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
