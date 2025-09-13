package mango.parser;

import java.util.stream.Stream;

/**
 * Enumerates all valid commands recognized by {@code MangoBot}.
 */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    FIND("find"),
    UNKNOWN("");

    private final String keyword;

    /**
     * Constructs a {@code Command} with its associated keyword.
     *
     * @param keyword the string that identifies this command.
     */
    Command(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the command that matches the start of the input.
     *
     * @param input the raw user input.
     * @return the parsed command, or {@link #UNKNOWN} if none matches.
     */
    public static Command of(String input) {
        return Stream.of(values())
                .filter(c -> input.startsWith(c.keyword))
                .findFirst()
                .orElse(UNKNOWN);
    }

    /**
     * Trims the command keyword from the beginning of the input.
     *
     * @param input the full input string containing the command and its argument.
     * @return the argument portion with leading and trailing whitespace removed.
     */
    public String trimKeyword(String input) {
        assert input != null : "trimKeyword expects non-null input";
        assert input.startsWith(this.keyword) : "Input should start with command keyword (or UNKNOWN)";
        String argument = input.substring(this.keyword.length());
        return argument.trim();
    }
}
