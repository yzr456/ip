package mango.ui;

import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        String wrappedMessage = wrap(message);
        System.out.println(wrappedMessage);
    }

    public void showWelcome() {
        String wrappedWelcomeMessage = Messages.welcome();
        System.out.println(wrappedWelcomeMessage);
    }

    public void showBye() {
        String wrappedByeMessage = Messages.bye();
        System.out.println(wrappedByeMessage);
    }

    public void showError(String message) {
        String wrappedErrorMessage = Messages.error(message);
        System.out.println(wrappedErrorMessage);
    }

    public void showFailedSave() {
        String wrappedFailedSaveMessage = Messages.failedSave();
        System.out.println(wrappedFailedSaveMessage);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    private String wrap(String body) {
        return LINE + "\n " + body + "\n" + LINE;
    }
}
