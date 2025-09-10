package mango.exception;

/**
 * Exception type for MangoBot-specific validation and parsing errors.
 */
public class MangoException extends Exception {
    public static final String ERR_INVALID = "Invalid input.";
    public static final String ERR_NAN = "Please provide a valid number.";

    public static final String ERR_MARK_EMPTY = "The index of the Task to mark must be specified.";
    public static final String ERR_MARK_RANGE = "The index of the Task to mark must be within the list.";

    public static final String ERR_UNMARK_EMPTY = "The index of the Task to unmark must be specified.";
    public static final String ERR_UNMARK_RANGE = "The index of the Task to unmark must be within the list.";

    public static final String ERR_TODO_EMPTY = "Todo description cannot be empty.";
    public static final String ERR_DEADLINE_EMPTY = "Deadline description cannot be empty.";
    public static final String ERR_EVENT_EMPTY = "Event description cannot be empty.";

    public static final String ERR_DELETE_EMPTY = "The index of the Task to be removed must be specified.";
    public static final String ERR_DELETE_RANGE = "The index of the Task to removed must be within the list.";

    public static final String ERR_FIND_EMPTY = "The keyword for Find cannot be empty.";

    /**
     * Constructs a {@code MangoException} with the specified error message.
     *
     * @param message the error message
     */
    public MangoException(String message) {
        super(message);
    }
}
