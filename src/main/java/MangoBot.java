import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class MangoBot {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) throws IOException, MangoException {
        Storage storage = new Storage("./data/mango.txt");
        storage.init();
        List<Task> tasks = storage.load();
        System.out.println(LINE + "\n Hello! I'm MangoBot\n" +
                " What can I do for you?\n" + LINE);
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            Command cmd = Command.of(input);
            switch (cmd) {
                case BYE -> {
                    System.out.println(LINE + "\n Bye. Hope to see you again soon!\n" + LINE);
                    sc.close();
                    return;
                }

                case LIST -> {
                    System.out.println(LINE + "\n Here are the tasks in your list:\n" + LINE);
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks have been added yet.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i + 1) + "." + tasks.get(i));
                        }
                    }
                }

                case MARK -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty()) throw new MangoException("The index of the Task to mark must be specified");

                    int index = Integer.parseInt(arg);
                    if (index <= 0 || index > tasks.size()) throw new MangoException(
                            "The index of the Task to mark must be within the list.");

                    Task t = tasks.get(index - 1);
                    t.markAsDone();
                    storage.save(tasks);

                    System.out.println(LINE + "\n Nice! I've marked this task as done:\n   " + t + "\n" + LINE);
                }

                case UNMARK -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty()) throw new MangoException("The index of the Task to unmark must be specified");

                    int index = Integer.parseInt(arg);
                    if (index <= 0 || index > tasks.size()) throw new MangoException(
                            "The index of the Task to unmark must be within the list.");

                    Task t = tasks.get(index - 1);
                    t.markAsNotDone();
                    storage.save(tasks);

                    System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n   " + t + "\n" + LINE);
                }

                case TODO -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty()) throw new MangoException("Todo description cannot be empty.");

                    Task t = new Todo(arg);
                    tasks.add(t);
                    storage.save(tasks);

                    System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                            "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);

                }

                case DEADLINE -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty()) throw new MangoException("Deadline description cannot be empty.");

                    String[] parts = cmd.arg(input).split(" /by ", 2);
                    Task t = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(t);
                    storage.save(tasks);

                    System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                            "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
                }

                case EVENT -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty()) throw new MangoException("Event description cannot be empty.");

                    int iFrom = arg.indexOf(" /from ");
                    int iTo = arg.indexOf(" /to ", iFrom + 7);
                    String desc = arg.substring(0, iFrom).trim();
                    String from = arg.substring(iFrom + 7, iTo).trim();
                    String to = arg.substring(iTo + 5).trim();
                    Task t = new Event(desc, from, to);
                    tasks.add(t);
                    storage.save(tasks);

                    System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                            "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
                }

                case DELETE -> {
                    String arg = cmd.arg(input);
                    if (arg.isEmpty())
                        throw new MangoException("The index of the Task to be removed must be specified");

                    int index = Integer.parseInt(arg);
                    if (index <= 0 || index > tasks.size()) throw new MangoException(
                            "The index of the Task to removed must be within the list.");

                    Task removed = tasks.remove(index - 1);
                    storage.save(tasks);

                    System.out.println(LINE + "\n Noted. I've removed this task:\n   " + removed +
                            "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
                }

                case UNKNOWN -> throw new MangoException("Invalid input.");
            }
        }
    }
}