package mango.ui;

import java.util.List;

import mango.task.Task;

public final class Messages {
    public static final String LINE = "____________________________________________________________";

    private Messages() {
    }

    private static String wrap(String body) {
        return LINE + "\n " + body + "\n" + LINE;
    }

    private static String welcomeBody() {
        return "Hello! I'm MangoBot\n What can I do for you?";
    }

    private static String byeBody() {
        return "Bye. Hope to see you again soon!";
    }

    private static String invalidBody() {
        return "Invalid input.";
    }

    private static String failedSaveBody() {
        return "Failed to save tasks to disk.";
    }

    private static String errorBody(String message) {
        return message;
    }

    private static String addedBody(Task t, int count) {
        return "Got it. I've added this task:\n   "
                + t + "\nNow you have " + count + " tasks in the list.";
    }

    private static String removedBody(Task removed, int count) {
        return "Noted. I've removed this task:\n   "
                + removed + "\nNow you have " + count + " tasks in the list.";
    }

    private static String markedBody(Task t) {
        return "Nice! I've marked this task as done:\n   " + t;
    }

    private static String unmarkedBody(Task t) {
        return "OK, I've marked this task as not done yet:\n   " + t;
    }

    private static String listBody(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            sb.append("\nNo tasks have been added yet.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("\n ").append(i + 1).append(".").append(tasks.get(i));
            }
        }
        return sb.toString();
    }

    private static String foundBody(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        if (tasks.isEmpty()) {
            sb.append("\nNo matching tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("\n ").append(i + 1).append(".").append(tasks.get(i));
            }
        }
        return sb.toString();
    }

    public static String welcomePlain() {
        return welcomeBody();
    }

    public static String byePlain() {
        return byeBody();
    }

    public static String invalidPlain() {
        return invalidBody();
    }

    public static String failedSavePlain() {
        return failedSaveBody();
    }

    public static String errorPlain(String m) {
        return errorBody(m);
    }

    public static String addedPlain(Task t, int c) {
        return addedBody(t, c);
    }

    public static String removedPlain(Task t, int c) {
        return removedBody(t, c);
    }

    public static String markedPlain(Task t) {
        return markedBody(t);
    }

    public static String unmarkedPlain(Task t) {
        return unmarkedBody(t);
    }

    public static String listPlain(List<Task> ts) {
        return listBody(ts);
    }

    public static String foundPlain(List<Task> ts) {
        return foundBody(ts);
    }

    public static String welcome() {
        return wrap(welcomeBody());
    }

    public static String bye() {
        return wrap(byeBody());
    }

    public static String invalid() {
        return wrap(invalidBody());
    }

    public static String failedSave() {
        return wrap(failedSaveBody());
    }

    public static String error(String m) {
        return wrap(errorBody(m));
    }

    public static String added(Task t, int c) {
        return wrap(addedBody(t, c));
    }

    public static String removed(Task t, int c) {
        return wrap(removedBody(t, c));
    }

    public static String marked(Task t) {
        return wrap(markedBody(t));
    }

    public static String unmarked(Task t) {
        return wrap(unmarkedBody(t));
    }

    public static String list(List<Task> ts) {
        return wrap(listBody(ts));
    }

    public static String found(List<Task> ts) {
        return wrap(foundBody(ts));
    }
}
