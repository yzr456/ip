package mango.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mango.exception.MangoException;
import mango.io.Storage;
import mango.parser.Command;
import mango.parser.Parser;
import mango.task.Task;
import mango.task.TaskList;
import mango.ui.Messages;
import mango.ui.Ui;

/**
 * Entry point of the chatbot application.
 *
 * <p>Manages user interaction, task management, and persistent storage. Supports commands
 * such as {@code todo}, {@code deadline}, {@code event}, {@code list}, {@code mark},
 * {@code unmark}, {@code delete}, {@code find}, and {@code bye}.</p>
 *
 * <p>On construction, prints a welcome message, initializes storage, and attempts to load
 * tasks from disk. If loading fails, a user-visible message is recorded in
 * {@link #getStartupErrorMessage()} for GUI display.</p>
 */
public class MangoBot {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;
    private String startupErrorMessage;

    /**
     * Constructs a {@code MangoBot} with the backing storage file.
     *
     * @param filePath Path to the file where tasks will be saved and loaded.
     * @throws IOException If storage initialization fails.
     */
    public MangoBot(String filePath) throws IOException {
        this.ui = new Ui();
        ui.showMessage(Messages.welcome());
        this.storage = new Storage(filePath);
        List<Task> loadedTasks;
        try {
            loadedTasks = this.storage.load();
        } catch (IOException e) {
            this.startupErrorMessage = Messages.failedLoad();
            ui.showMessage(Messages.failedLoad());
            loadedTasks = new ArrayList<>();
        }
        this.taskList = new TaskList(loadedTasks);
    }

    /**
     * Returns MangoBot's reply for a single line of user input.
     *
     * @param input The raw user input.
     * @return The formatted reply text.
     */
    public String respond(String input) {
        assert input != null : "respond() expects non-null input";
        try {
            return buildReplyForInput(input);
        } catch (MangoException e) {
            return Messages.error(e.getMessage());
        } catch (IOException e) {
            return Messages.failedSave();
        }
    }

    /**
     * Runs the console interface loop until the {@code bye} command is issued.
     */
    public void run() {
        while (true) {
            String input = ui.readCommand();
            assert input != null : "Console input should not be null";

            if (commandIsBye(input)) {
                ui.showMessage(Messages.bye());
                ui.close();
                return;
            }

            ui.showMessage(respond(input));
        }
    }

    /**
     * Returns the startup error message if tasks failed to load during construction.
     *
     * @return the startup error message, or {@code null} if no load error occurred
     */
    public String getStartupErrorMessage() {
        return this.startupErrorMessage;
    }

    /**
     * Parses, validates, and dispatches a single input into a reply.
     *
     * @param input the raw user input
     * @return the formatted reply text
     * @throws MangoException if command-specific validation fails
     * @throws IOException if saving to storage fails in the dispatched handler
     */
    private String buildReplyForInput(String input) throws MangoException, IOException {
        Parser p = new Parser(input);
        p.validateArgument();
        return getMessage(p);
    }

    /**
     * Returns {@code true} if the input corresponds to the {@code bye} command.
     *
     * @param input the raw user input
     * @return whether the command is {@code bye}
     */
    private boolean commandIsBye(String input) {
        return new Parser(input).getCommand() == Command.BYE;
    }

    /**
     * Builds the user-visible message for an already-validated {@link Parser}.
     *
     * <p>Dispatches to command handlers and, where applicable, persists changes
     * before formatting the response.</p>
     *
     * @param p the parser constructed from the raw input (already validated)
     * @return a formatted message for the command
     * @throws MangoException if command-specific validation fails
     * @throws IOException if saving to storage fails
     */
    private String getMessage(Parser p) throws MangoException, IOException {
        assert p.getCommand() != null : "Parser must set a non-null command";
        return switch (p.getCommand()) {
            case BYE -> Messages.bye();
            case LIST -> handleList(p);
            case MARK -> handleMark(p);
            case UNMARK -> handleUnmark(p);
            case TODO, EVENT, DEADLINE -> handleAdd(p);
            case DELETE -> handleDelete(p);
            case FIND -> handleFind(p);
            default -> Messages.invalid();
        };
    }

    private String handleList(Parser p) {
        return Messages.list(taskList.view());
    }

    private String handleMark(Parser p) throws MangoException, IOException {
        List<Integer> indices = p.parseMultipleIndices(taskList.size());
        List<Task> marked = taskList.mark(indices);
        storage.save(taskList.view());
        return Messages.marked(marked);
    }

    private String handleUnmark(Parser p) throws MangoException, IOException {
        List<Integer> indices = p.parseMultipleIndices(taskList.size());
        List<Task> unmarked = taskList.unmark(indices);
        storage.save(taskList.view());
        return Messages.unmarked(unmarked);
    }

    private String handleAdd(Parser p) throws MangoException, IOException {
        Task t = taskList.add(p.parseArgument());
        storage.save(taskList.view());
        return Messages.added(t, taskList.size());
    }

    private String handleDelete(Parser p) throws MangoException, IOException {
        var indices = p.parseMultipleIndices(taskList.size());
        var removed = taskList.remove(indices);
        storage.save(taskList.view());
        return Messages.removed(removed, taskList.size());
    }

    private String handleFind(Parser p) {
        return Messages.found(taskList.find(p.getArgument()));
    }

    /**
     * Creates and runs a new {@code MangoBot} instance.
     *
     * @param args Command-line arguments (unused).
     * @throws IOException If storage initialization fails.
     */
    public static void main(String[] args) throws IOException {
        new MangoBot("./data/mango.txt").run();
    }
}
