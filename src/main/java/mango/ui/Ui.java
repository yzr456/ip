package mango.ui;

import java.util.List;
import java.util.Scanner;

import mango.task.Task;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(Messages.welcome());
    }

    public void showBye() {
        System.out.println(Messages.bye());
    }

    public void showError(String message) {
        System.out.println(Messages.error(message));
    }

    public void showInvalid() {
        System.out.println(Messages.invalid());
    }

    public void showFailedSave() {
        System.out.println(Messages.failedSave());
    }

    public void showAdded(Task t, int count) {
        System.out.println(Messages.added(t, count));
    }

    public void showRemoved(Task removed, int count) {
        System.out.println(Messages.removed(removed, count));
    }

    public void showMarked(Task t) {
        System.out.println(Messages.marked(t));
    }

    public void showUnmarked(Task t) {
        System.out.println(Messages.unmarked(t));
    }

    public void showList(List<Task> tasks) {
        System.out.println(Messages.list(tasks));
    }

    public void showFound(List<Task> tasks) {
        System.out.println(Messages.found(tasks));
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
