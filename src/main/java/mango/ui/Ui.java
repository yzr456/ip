package mango.ui;

import java.util.Scanner;

/**
 * Console I/O helper that prints messages and reads user commands.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a wrapped, user-visible message.
     *
     * @param message The message to show.
     */
    public void showMessage(String message) {
        String wrappedMessage = wrap(message);
        System.out.println(wrappedMessage);
    }

    /**
     * Prints the welcome message.
     */
    public void showWelcome() {
        String wrappedWelcomeMessage = wrap(Messages.welcome());
        System.out.println(wrappedWelcomeMessage);
    }

    /**
     * Prints the farewell message.
     */
    public void showBye() {
        String wrappedByeMessage = wrap(Messages.bye());
        System.out.println(wrappedByeMessage);
    }

    /**
     * Prints an error message.
     *
     * @param message The error text.
     */
    public void showError(String message) {
        String wrappedErrorMessage = wrap(Messages.error(message));
        System.out.println(wrappedErrorMessage);
    }

    /**
     * Prints a message indicating a storage-loading failure.
     */
    public void showFailedLoad() {
        String wrappedFailedLoadMessage = wrap(Messages.failedLoad());
        System.out.println(wrappedFailedLoadMessage);
    }

    /**
     * Prints a message indicating a storage-saving failure.
     */
    public void showFailedSave() {
        String wrappedFailedSaveMessage = wrap(Messages.failedSave());
        System.out.println(wrappedFailedSaveMessage);
    }

    /**
     * Reads one line of user input from {@code System.in}.
     *
     * @return The raw input line.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the underlying input scanner.
     */
    public void close() {
        scanner.close();
    }

    private String wrap(String body) {
        return LINE + "\n" + body + "\n" + LINE;
    }
}
