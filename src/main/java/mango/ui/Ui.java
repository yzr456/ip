package mango.ui;

import java.util.List;
import java.util.Scanner;
import mango.task.Task;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE + "\n Hello! I'm MangoBot\n What can I do for you?\n" + LINE);
    }

    public void showBye() {
        System.out.println(LINE + "\n Bye. Hope to see you again soon!\n" + LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void showError(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }

    public void showAdded(Task t, int count) {
        System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                "\n Now you have " + count + " tasks in the list.\n" + LINE);
    }

    public void showRemoved(Task removed, int count) {
        System.out.println(LINE + "\n Noted. I've removed this task:\n   " + removed +
                "\n Now you have " + count + " tasks in the list.\n" + LINE);
    }

    public void showMarked(Task t) {
        System.out.println(LINE + "\n Nice! I've marked this task as done:\n   " + t + "\n" + LINE);
    }

    public void showUnmarked(Task t) {
        System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n   " + t + "\n" + LINE);
    }

    public void showList(List<Task> tasks) {
        System.out.println(LINE + "\n Here are the tasks in your list:\n");
        if (tasks.isEmpty()) {
            System.out.println("No tasks have been added yet.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println(LINE);
    }

    public void showFound(List<Task> tasks) {
        System.out.println(LINE + "\n Here are the matching tasks in your list for:");
        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println(LINE);
    }

}
