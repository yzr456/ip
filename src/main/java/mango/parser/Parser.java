package mango.parser;

import java.util.ArrayList;
import java.util.List;

import mango.exception.MangoException;
import mango.task.Deadline;
import mango.task.Event;
import mango.task.Task;
import mango.task.Todo;

/**
 * Parses user input into a {@link Command} and extracts arguments.
 */
public class Parser {
    private static final String BY_DELIMITER = " /by ";
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";
    private static final int FROM_DELIMITER_LENGTH = FROM_DELIMITER.length();
    private static final int TO_DELIMITER_LENGTH = TO_DELIMITER.length();
    private final Command command;
    private final String argument;

    /**
     * Constructs a {@code Parser} from raw user input.
     *
     * @param input The full command string entered by the user.
     */
    public Parser(String input) {
        assert input != null : "Parser expects non-null raw input";
        this.command = Command.of(input);
        this.argument = this.command.trimKeyword(input);
        assert this.command != null : "Command must not be null";
        assert this.argument != null : "Argument must not be null (empty string is fine)";
    }

    /**
     * Returns the parsed command.
     *
     * @return The command.
     */
    public Command getCommand() {
        return this.command;
    }

    /**
     * Returns the argument portion of the user input.
     *
     * @return The argument string, possibly empty.
     */
    public String getArgument() {
        return this.argument;
    }

    /**
     * Validates that the current command has a valid argument.
     *
     * @throws MangoException If the argument is missing or invalid.
     */
    public void validateArgument() throws MangoException {
        validateArgumentPresence();
        validateArgumentFormat();
    }

    /**
     * Parses the current argument into a {@link Task} based on the command.
     *
     * @return A new {@link Task}.
     * @throws MangoException If the command is not a creation command or the argument is invalid.
     */
    public Task parseArgument() throws MangoException {
        return switch (this.command) {
            case TODO -> parseTodo();
            case DEADLINE -> parseDeadline();
            case EVENT -> parseEvent();
            default -> throw new MangoException(MangoException.ERR_INVALID);
        };
    }

    /**
     * Parses multiple indices from the argument string.
     *
     * @param listSize The total number of tasks.
     * @return A list of zero-based indices.
     * @throws MangoException If any index is invalid.
     */
    public List<Integer> parseMultipleIndices(int listSize) throws MangoException {
        assert listSize >= 0 : "Task list size must be non-negative";
        validateArgumentPresence();

        String[] oneBasedIndices = this.argument.split("\\s+");
        return toZeroBasedIndices(oneBasedIndices, listSize);
    }

    /**
     * Converts whitespace-separated 1-based indices into zero-based indices and validates range.
     *
     * @param indices the string tokens parsed from {@link #argument}
     * @param listSize the current number of tasks for range checking
     * @return a list of zero-based indices
     * @throws MangoException if any index is not numeric or out of range
     */
    private List<Integer> toZeroBasedIndices(String[] indices, int listSize) throws MangoException {
        List<Integer> zeroBasedIndices = new ArrayList<>(indices.length);
        for (String index : indices) {
            int oneBasedIndex = parseOneBasedIndex(index);
            validateRange(oneBasedIndex, listSize);
            zeroBasedIndices.add(oneBasedIndex - 1);
        }
        return zeroBasedIndices;
    }

    private void validateArgumentPresence() throws MangoException {
        if (!this.argument.isEmpty()) {
            return;
        }

        switch (this.command) {
            case MARK -> throw new MangoException(MangoException.ERR_MARK_EMPTY);
            case UNMARK -> throw new MangoException(MangoException.ERR_UNMARK_EMPTY);
            case TODO -> throw new MangoException(MangoException.ERR_TODO_EMPTY);
            case EVENT -> throw new MangoException(MangoException.ERR_EVENT_EMPTY);
            case DEADLINE -> throw new MangoException(MangoException.ERR_DEADLINE_EMPTY);
            case DELETE -> throw new MangoException(MangoException.ERR_DELETE_EMPTY);
            case FIND -> throw new MangoException(MangoException.ERR_FIND_EMPTY);
            default -> { }
        }
    }

    private void validateArgumentFormat() throws MangoException {
        switch (this.command) {
            case DEADLINE -> handleDeadlineFormat();
            case EVENT -> handleEventFormat();
            default -> { }
        }
    }

    private void handleDeadlineFormat() throws MangoException {
        if (!this.argument.contains(BY_DELIMITER)) {
            throw new MangoException("Deadline must use format: deadline <desc> " + BY_DELIMITER + "<time>");
        }
    }

    private void handleEventFormat() throws MangoException {
        if (!this.argument.contains(FROM_DELIMITER) || !this.argument.contains(TO_DELIMITER)) {
            throw new MangoException("Event must use format: event <desc> "
                    + FROM_DELIMITER + "<start> " + TO_DELIMITER + "<end>");
        }
    }

    private Task parseTodo() {
        assert !this.argument.isEmpty() : "Argument must have description";
        return new Todo(this.argument);
    }

    private Task parseDeadline() throws MangoException {
        String[] parts = this.argument.split(BY_DELIMITER, 2);
        assert parts.length == 2 : "Argument must have " + BY_DELIMITER;
        String desc = parts[0].trim();
        String by = parts[1].trim();

        assertNonBlank(desc, "Deadline description must be non-empty");
        assertNonBlank(by, "Deadline date/time must be non-empty");

        return constructDeadline(desc, by);
    }

    private Task parseEvent() throws MangoException {
        int indexOfFrom = this.argument.indexOf(FROM_DELIMITER);
        int indexOfTo = this.argument.indexOf(TO_DELIMITER, indexOfFrom + FROM_DELIMITER_LENGTH);
        assert indexOfFrom >= 0 && indexOfTo > indexOfFrom : "Argument must have "
                + FROM_DELIMITER + " and " + TO_DELIMITER;
        String desc = this.argument.substring(0, indexOfFrom).trim();
        String from = this.argument.substring(indexOfFrom + FROM_DELIMITER_LENGTH, indexOfTo).trim();
        String to = this.argument.substring(indexOfTo + TO_DELIMITER_LENGTH).trim();

        assertNonBlank(desc, "Event description must be non-empty");
        assertNonBlank(from, "Event start time must be non-empty");
        assertNonBlank(to, "Event end time must be non-empty");

        return constructEvent(desc, from, to);
    }

    /**
     * Asserts the string is non-null and non-empty (assertions only).
     *
     * @param s value to check
     * @param message assertion message
     */
    private void assertNonBlank(String s, String message) {
        assert s != null;
        assert !s.isEmpty() : message;
    }

    /**
     * Parses a single 1-based index token into an {@code int}.
     *
     * @param argument the token to parse
     * @return the parsed 1-based index
     * @throws MangoException if the token is not a valid integer
     */
    private int parseOneBasedIndex(String argument) throws MangoException {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            throw new MangoException(MangoException.ERR_NAN);
        }
    }

    private Task constructDeadline(String desc, String by) throws MangoException {
        try {
            return new Deadline(desc, by);
        } catch (IllegalArgumentException e) {
            throw new MangoException(e.getMessage());
        }
    }

    private Task constructEvent(String desc, String from, String to) throws MangoException {
        try {
            return new Event(desc, from, to);
        } catch (IllegalArgumentException e) {
            throw new MangoException(e.getMessage());
        }
    }

    /**
     * Validates that a 1-based index lies within {@code 1..listSize}.
     *
     * @param oneBasedIndex index to validate
     * @param listSize upper bound (inclusive)
     * @throws MangoException if out of range (command-specific message where applicable)
     */
    private void validateRange(int oneBasedIndex, int listSize) throws MangoException {
        assert oneBasedIndex != 0 : "A zero index would be invalid for 1-based input";
        if (oneBasedIndex <= 0 || oneBasedIndex > listSize) {
            switch (this.command) {
                case MARK -> throw new MangoException(MangoException.ERR_MARK_RANGE);
                case UNMARK -> throw new MangoException(MangoException.ERR_UNMARK_RANGE);
                case DELETE -> throw new MangoException(MangoException.ERR_DELETE_RANGE);
                default -> throw new MangoException(MangoException.ERR_INVALID);
            }
        }
    }
}
