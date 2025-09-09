package mango.parser;

import mango.exception.MangoException;
import mango.task.Deadline;
import mango.task.Event;
import mango.task.Task;
import mango.task.Todo;

/**
 * The {@code Parser} class is responsible for parsing user input
 * into a {@link Command} and extracting relevant arguments.
 */
public class Parser {
    private final Command command;
    private final String argument;

    /**
     * Constructs a {@code Parser} from raw user input.
     *
     * @param input the full command string entered by the user
     */
    public Parser(String input) {
        assert input != null : "Parser expects non-null raw input";
        this.command = Command.of(input);
        this.argument = this.command.trimArgument(input);
        assert this.command != null : "Command must not be null";
        assert this.argument != null : "Argument must not be null (empty string is fine)";
    }

    /**
     * Returns the parsed {@link Command}.
     *
     * @return the command
     */
    public Command getCommand() {
        return this.command;
    }

    /**
     * Returns the argument portion of the user input
     *
     * @return the argument string, possibly empty
     */
    public String getArgument() {
        return this.argument;
    }

    /**
     * Validates that the current {@link Command} has a valid argument.
     *
     * @throws MangoException if the argument is missing or invalid
     */
    public void validateArgument() throws MangoException {
        if (this.argument.isEmpty()) {
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

        switch (this.command) {
        case DEADLINE -> {
            if (!this.argument.contains(" /by ")) {
                throw new MangoException("Deadline must use format: deadline <desc> /by <time>");
            }
        }

        case EVENT -> {
            if (!this.argument.contains(" /from ") || !this.argument.contains(" /to ")) {
                throw new MangoException("Event must use format: event <desc> /from <start> /to <end>");
            }
        }

        default -> { }
        }
    }

    /**
     * Parses the current argument into a {@link Task} object based on the {@link Command}.
     *
     * @return a new {@link Task} instance
     * @throws MangoException if the command is not a task-creation command or the argument is invalid
     */
    public Task parseArgument() throws MangoException {
        switch (this.command) {
        case TODO -> {
            assert !this.argument.isEmpty() : "TODO requires non-empty description (should be validated)";
            return new Todo(this.argument);
        }

        case DEADLINE -> {
            String[] parts = this.argument.split(" /by ", 2);
            assert parts.length == 2 : "validateArgument has /by";
            assert !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty()
                    : "Both desc and by must be non-empty";
            return new Deadline(parts[0].trim(), parts[1].trim());
        }

        case EVENT -> {
            int indexOfFrom = this.argument.indexOf(" /from ");
            int indexOfTo = this.argument.indexOf(" /to ", indexOfFrom + 7);
            assert indexOfFrom >= 0 && indexOfTo > indexOfFrom : "validateArgument has /from and /to";
            String desc = this.argument.substring(0, indexOfFrom).trim();
            String from = this.argument.substring(indexOfFrom + 7, indexOfTo).trim();
            String to = this.argument.substring(indexOfTo + 5).trim();
            assert !desc.isEmpty() && !from.isEmpty() && !to.isEmpty() : "desc/from/to must be non-empty";
            return new Event(desc, from, to);
        }

        default -> throw new MangoException(MangoException.ERR_INVALID);
        }
    }

    /**
     * Parses an index argument (for mark, unmark, delete commands).
     *
     * @param size the number of tasks in the list
     * @return the zero-based index
     * @throws MangoException if the index is missing, non-numeric, or out of range
     */
    public int parseIndex(int size) throws MangoException {
        assert size >= 0 : "Task list size must be non-negative";
        int oneBasedIndex;
        try {
            oneBasedIndex = Integer.parseInt(this.argument);
        } catch (NumberFormatException e) {
            throw new MangoException(MangoException.ERR_NAN);
        }
        assert oneBasedIndex != 0 : "A zero index would be invalid for 1-based input";
        if (oneBasedIndex <= 0 || oneBasedIndex > size) {
            switch (this.command) {
            case MARK -> throw new MangoException(MangoException.ERR_MARK_RANGE);
            case UNMARK -> throw new MangoException(MangoException.ERR_UNMARK_RANGE);
            case DELETE -> throw new MangoException(MangoException.ERR_DELETE_RANGE);
            default -> throw new MangoException(MangoException.ERR_INVALID);
            }
        }
        int zeroBasedIndex = oneBasedIndex - 1;
        assert zeroBasedIndex >= 0 && zeroBasedIndex < size : "Returned index must be within bounds";
        return zeroBasedIndex;
    }
}
