package mango.core;

import java.io.IOException;
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
 * The {@code MangoBot} class represents the main entry point of the chatbot application.
 * It manages user interaction, task management, and storage of tasks.
 *
 * <p>The bot supports commands such as {@code todo}, {@code deadline}, {@code event},
 * {@code list}, {@code mark}, {@code unmark}, {@code delete}, and {@code bye}.
 */
public class MangoBot {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs a {@code MangoBot} instance with the given file path for storage.
     *
     * @param filePath path to the file where tasks will be saved and loaded
     * @throws IOException if an error occurs while initializing the storage
     */
    public MangoBot(String filePath) throws IOException {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(this.storage.load());
    }

    /**
     * Returns MangoBot's reply for a single line of user input.
     *
     * @param input the raw user input
     * @return the formatted reply text
     */
    public String respond(String input) {
        assert input != null : "respond() expects non-null input";
        Parser p = new Parser(input);
        try {
            p.validateArgument();
            return getMessage(p);
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
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            assert input != null : "Console input should not be null";
            Parser p = new Parser(input);

            if (p.getCommand() == Command.BYE) {
                ui.showBye();
                ui.close();
                return;
            }

            try {
                p.validateArgument();
                ui.showMessage(getMessage(p));
            } catch (MangoException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showFailedSave();
            }
        }
    }

    /**
     * Builds the user-visible message for an already-validated {@link Parser}.
     *
     * @param p a parser constructed from the raw input
     * @return a formatted message for the command
     * @throws MangoException if command-specific validation fails
     * @throws IOException if saving to storage fails
     */
    private String getMessage(Parser p) throws MangoException, IOException {
        assert p.getCommand() != null : "Parser must set a non-null command";
        return switch (p.getCommand()) {
            case BYE -> Messages.bye();
            case LIST -> Messages.list(taskList.view());
            case MARK -> {
                List<Integer> indices = p.parseMultipleIndices(taskList.size());
                List<Task> marked = taskList.mark(indices);
                storage.save(taskList.view());
                yield Messages.marked(marked);
            }
            case UNMARK -> {
                List<Integer> indices = p.parseMultipleIndices(taskList.size());
                List<Task> unmarked = taskList.unmark(indices);
                storage.save(taskList.view());
                yield Messages.unmarked(unmarked);
            }
            case TODO, EVENT, DEADLINE -> {
                Task t = taskList.add(p.parseArgument());
                assert taskList.view().contains(t) : "Added task must be present in list";
                storage.save(taskList.view());
                yield Messages.added(t, taskList.size());
            }
            case DELETE -> {
                List<Integer> indices = p.parseMultipleIndices(taskList.size());
                List<Task> removed = taskList.remove(indices);
                storage.save(taskList.view());
                yield Messages.removed(removed, taskList.size());
            }
            case FIND -> Messages.found(taskList.find(p.getArgument()));
            default -> Messages.invalid();
        };
    }

    /**
     * Creates and runs a new {@code MangoBot} instance.
     *
     * @throws IOException if storage initialization fails
     */
    public static void main(String[] args) throws IOException {
        new MangoBot("./data/mango.txt").run();
    }
}
