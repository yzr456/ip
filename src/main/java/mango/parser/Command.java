package mango.parser;

/**
 * Enum representing all valid commands recognized by {@code MangoBot}.
 */
public enum Command {
    BYE("bye", false),
    LIST("list", false),
    MARK("mark", true),
    UNMARK("unmark", true),
    TODO("todo", true),
    DEADLINE("deadline", true),
    EVENT("event", true),
    DELETE("delete", true),
    UNKNOWN("", false);

    private final String keyword;
    private final boolean needsArg;

    Command(String keyword, boolean needsArg) {
        this.keyword = keyword;
        this.needsArg = needsArg;
    }

    /**
     * Parses input to determine which {@code Command} it represents.
     *
     * @param input the raw user input
     * @return the parsed command, or {@link #UNKNOWN} if none matches
     */
    public static Command of(String input) {
        for (Command c : values()) {
            if (c.keyword.equals(input) || input.startsWith(c.keyword + " ")) {
                return c;
            }
        }
        return UNKNOWN;
    }

    /**
     * Extracts argument from input for this command.
     *
     * @param input the raw user input
     * @return the argument string (may be empty if no argument is required)
     */
    public String arg(String input) {
        if (!needsArg) {
            return "";
        }
        return input.substring(this.keyword.length()).trim();
    }
}
