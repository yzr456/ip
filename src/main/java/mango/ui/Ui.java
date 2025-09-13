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
     * Reads one line of user input from {@code System.in}.
     *
     * @return the raw input line.
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
