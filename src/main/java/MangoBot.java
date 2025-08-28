import java.io.IOException;

class MangoBot {
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    public MangoBot(String filePath) throws IOException {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(this.storage.load());
    }

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
                }
            } catch (MangoException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Failed to save tasks to disk.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new MangoBot("./data/mango.txt").run();
    }
}