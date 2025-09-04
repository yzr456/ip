package mango.core;

import java.io.IOException;

import mango.exception.MangoException;
import mango.io.Storage;
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

    public String respond(String input) {
        Parser p = new Parser(input);
        try {
            p.validateArgument();
            return switch (p.getCommand()) {
            case BYE -> Messages.byePlain();
            case LIST -> Messages.listPlain(taskList.view());
            case MARK -> {
                int i = p.parseIndex(taskList.size());
                Task t = taskList.mark(i);
                storage.save(taskList.view());
                yield Messages.markedPlain(t);
            }

            case UNMARK -> {
                int i = p.parseIndex(taskList.size());
                Task t = taskList.unmark(i);
                storage.save(taskList.view());
                yield Messages.unmarkedPlain(t);
            }

            case TODO, EVENT, DEADLINE -> {
                Task t = taskList.add(p.parseArgument());
                storage.save(taskList.view());
                yield Messages.addedPlain(t, taskList.size());
            }

            case DELETE -> {
                int i = p.parseIndex(taskList.size());
                Task r = taskList.remove(i);
                storage.save(taskList.view());
                yield Messages.removedPlain(r, taskList.size());
            }

            case FIND -> Messages.foundPlain(taskList.find(p.getArgument()));
            default -> Messages.invalidPlain();
            };
        } catch (MangoException e) {
            return Messages.errorPlain(e.getMessage());
        } catch (IOException e) {
            return Messages.failedSavePlain();
        }
    }

    /**
     * Runs the chatbot, continuously reading user input until the {@code bye} command is issued.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            Parser p = new Parser(input);

            try {
                p.validateArgument();
                switch (p.getCommand()) {
                case BYE -> {
                    ui.showBye();
                    ui.close();
                    return;
                }

                case LIST -> ui.showList(taskList.view());

                case MARK -> {
                    int idx = p.parseIndex(taskList.size());
                    Task t = taskList.mark(idx);
                    storage.save(taskList.view());
                    ui.showMarked(t);
                }

                case UNMARK -> {
                    int idx = p.parseIndex(taskList.size());
                    Task t = taskList.unmark(idx);
                    storage.save(taskList.view());
                    ui.showUnmarked(t);
                }

                case TODO, EVENT, DEADLINE -> {
                    Task t = taskList.add(p.parseArgument());
                    storage.save(taskList.view());
                    ui.showAdded(t, taskList.size());
                }

                case DELETE -> {
                    int idx = p.parseIndex(taskList.size());
                    Task removed = taskList.remove(idx);
                    storage.save(taskList.view());
                    ui.showRemoved(removed, taskList.size());
                }

                case FIND -> {
                    String keyword = p.getArgument();
                    ui.showFound(taskList.find(keyword));
                }

                default -> ui.showInvalid();

                }
            } catch (MangoException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showFailedSave();
            }
        }
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
