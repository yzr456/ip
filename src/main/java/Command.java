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

    public static Command of(String input) {
        for (Command c : values()) {
            if (c.keyword.equals(input) || input.startsWith(c.keyword + " ")) {
                return c;
            }
        }
        return UNKNOWN;
    }

    public String arg(String input) {
        if (!needsArg) {
            return "";
        }
        return input.substring(this.keyword.length()).trim();
    }
}
