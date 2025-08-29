package mango.parser;

import mango.exception.MangoException;

/**
 * The {@code Parser} class is responsible for parsing user input
 * into a {@link Command} and extracting relevant arguments.
 */
public class Parser {
    private final Command cmd;
    private final String arg;

    /**
     * Constructs a {@code Parser} from raw user input.
     *
     * @param input the full command string entered by the user
     */
    public Parser(String input) {
        this.cmd = Command.of(input);
        this.arg = this.cmd.arg(input);
    }

    /**
     * Returns the parsed {@link Command}.
     *
     * @return the command
     * @throws MangoException if the command is unknown
     */
    public Command getCmd() throws MangoException {
        if (this.cmd == Command.UNKNOWN) {
            throw new MangoException(MangoException.ERR_INVALID);
        }
        return this.cmd;
    }

    /**
     * Returns the argument part of the command after validation.
     *
     * @return the command argument
     * @throws MangoException if the argument is missing or invalid
     */
    public String getArg() throws MangoException {
        switch (this.cmd) {
            case TODO -> {
                if (this.arg.isEmpty()) throw new MangoException(MangoException.ERR_TODO_EMPTY);
            }
            case DEADLINE -> {
                if (this.arg.isEmpty()) throw new MangoException(MangoException.ERR_DEADLINE_EMPTY);
                if (!arg.contains(" /by ")) throw new MangoException("Deadline must use format: deadline <desc> /by <time>");
            }
            case EVENT -> {
                if (this.arg.isEmpty()) throw new MangoException(MangoException.ERR_EVENT_EMPTY);
                if (!arg.contains(" /from ") || !arg.contains(" /to ")) {
                    throw new MangoException("Event must use format: event <desc> /from <start> /to <end>");
                }
            }
            case FIND -> {
                if (this.arg.isEmpty()) throw new MangoException(MangoException.ERR_FIND_EMPTY);
            }
        }
        return this.arg;
    }

    /**
     * Parses an index argument (for mark, unmark, delete commands).
     *
     * @param size the number of tasks in the list
     * @return the zero-based index
     * @throws MangoException if the index is missing, non-numeric, or out of range
     */
    public int parseIndex(int size) throws MangoException {
        if (this.arg.isEmpty()) {
            switch (this.cmd) {
            case MARK -> throw new MangoException(MangoException.ERR_MARK_EMPTY);
            case UNMARK -> throw new MangoException(MangoException.ERR_UNMARK_EMPTY);
            case DELETE -> throw new MangoException(MangoException.ERR_DELETE_EMPTY);
            default -> throw new MangoException(MangoException.ERR_INVALID);
            }
        }

        int idx;
        try {
            idx = Integer.parseInt(this.arg);
        } catch (NumberFormatException e) {
            throw new MangoException(MangoException.ERR_NAN);
        }
        if (idx <= 0 || idx > size) {
            switch (this.cmd) {
            case MARK -> throw new MangoException(MangoException.ERR_MARK_RANGE);
            case UNMARK -> throw new MangoException(MangoException.ERR_UNMARK_RANGE);
            case DELETE -> throw new MangoException(MangoException.ERR_DELETE_RANGE);
            default -> throw new MangoException(MangoException.ERR_INVALID);
            }
        }
        return idx - 1;
    }
}
