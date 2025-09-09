package mango.parser;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Enum representing all valid commands recognized by {@code MangoBot}.
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
     * @param keyword the string that identifies this command
     */
    Command(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Parses input to determine which {@code Command} it represents.
     *
     * @param input the raw user input
     * @return the parsed command, or {@link #UNKNOWN} if none matches
     */
    public static Command of(String input) {
        return Stream.of(values())
                .filter(c -> input.startsWith(c.keyword))
                .findFirst()
                .orElse(UNKNOWN);
    }

    /**
     * Trims the command keyword from the beginning of the given input,
     * leaving only the argument portion (if any).
     *
     * @param input the full input string containing the command and its argument(s)
     * @return the argument portion of the input, with leading/trailing whitespace removed
     */
    public String trimKeyword(String input) {
        assert input != null : "trimKeyword expects non-null input";
        assert input.startsWith(this.keyword) : "Input should start with command keyword (or UNKNOWN)";
        String argument = input.substring(this.keyword.length());
        return argument.trim();
    }
}
