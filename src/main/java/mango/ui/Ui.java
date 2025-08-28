package mango.ui;

import java.util.List;
import java.util.Scanner;
import mango.task.Task;

/**
 * The {@code Ui} class manages all user interactions,
 * such as printing messages and reading user input.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /** Shows welcome message. */
    public void showWelcome() {
        System.out.println(LINE + "\n Hello! I'm MangoBot\n What can I do for you?\n" + LINE);
    }

    /** Shows goodbye message. */
    public void showBye() {
        System.out.println(LINE + "\n Bye. Hope to see you again soon!\n" + LINE);
    }

    /** @return next line of user input. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Closes the input scanner. */
    public void close() {
        scanner.close();
    }

    /** Prints an error message. */
    public void showError(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }

    /** Prints confirmation of task addition. */
    public void showAdded(Task t, int count) {
        System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                "\n Now you have " + count + " tasks in the list.\n" + LINE);
    }

    /** Prints confirmation of task removal. */
    public void showRemoved(Task removed, int count) {
        System.out.println(LINE + "\n Noted. I've removed this task:\n   " + removed +
                "\n Now you have " + count + " tasks in the list.\n" + LINE);
    }

    /** Prints confirmation of task being marked done. */
    public void showMarked(Task t) {
        System.out.println(LINE + "\n Nice! I've marked this task as done:\n   " + t + "\n" + LINE);
    }

    /** Prints confirmation of task being unmarked. */
    public void showUnmarked(Task t) {
        System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n   " + t + "\n" + LINE);
    }

    /** Shows the list of tasks. */
    public void showList(List<Task> tasks) {
        System.out.println(LINE + "\n Here are the tasks in your list:\n" + LINE);
        if (tasks.isEmpty()) {
            System.out.println("No tasks have been added yet.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
    }
}
