package mango.core;

import java.io.IOException;
import mango.io.Storage;
import mango.ui.Ui;
import mango.task.Task;
import mango.task.TaskList;
import mango.task.Todo;
import mango.task.Deadline;
import mango.task.Event;
import mango.parser.Parser;
import mango.exception.MangoException;

/**
 * The {@code MangoBot} class represents the main entry point of the chatbot application.
 * It manages user interaction, task management, and storage of tasks.
 *
 * <p>The bot supports commands such as {@code todo}, {@code deadline}, {@code event},
 * {@code list}, {@code mark}, {@code unmark}, {@code delete}, and {@code bye}.
 */
class MangoBot {
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
     * Runs the chatbot, continuously reading user input until the {@code bye} command is issued.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            Parser p = new Parser(input);

            try {
                switch (p.getCmd()) {
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

                    case TODO -> {
                        Task t = taskList.add(new Todo(p.getArg()));
                        storage.save(taskList.view());
                        ui.showAdded(t, taskList.size());
                    }

                    case DEADLINE -> {
                        String[] parts = p.getArg().split(" /by ", 2);
                        Task t = taskList.add(new Deadline(parts[0].trim(), parts[1].trim()));
                        storage.save(taskList.view());
                        ui.showAdded(t, taskList.size());
                    }

                    case EVENT -> {
                        String arg = p.getArg();
                        int iFrom = arg.indexOf(" /from ");
                        int iTo   = arg.indexOf(" /to ", iFrom + 7);
                        String desc = arg.substring(0, iFrom).trim();
                        String from = arg.substring(iFrom + 7, iTo).trim();
                        String to   = arg.substring(iTo + 5).trim();
                        Task t = taskList.add(new Event(desc, from, to));
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
                        String keyword = p.getArg();
                        ui.showFound(taskList.find(keyword));
                    }
                }
            } catch (MangoException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Failed to save tasks to disk.");
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
