import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MangoBot {
    private static final String LINE = "____________________________________________________________";
    public static void main(String[] args) throws MangoException {
        Scanner sc = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        System.out.println(LINE + "\n Hello! I'm MangoBot\n" +
                " What can I do for you?\n" + LINE);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println(LINE + "\n Bye. Hope to see you again soon!\n" + LINE);
                break;
            } else if (input.equals("list")) {
                System.out.println(LINE + "\n Here are the tasks in your list:\n" + LINE);
                if (tasks.isEmpty()) {
                    System.out.println("No tasks have been added yet.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                }
            } else if (input.startsWith("mark")) {
                if (input.length() == 4) throw new MangoException("The index of the Task to mark must be specified");
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task t = tasks.get(index);
                t.markAsDone();
                System.out.println(LINE + "\n Nice! I've marked this task as done:\n   " +
                        t + "\n" + LINE);
            } else if (input.startsWith("unmark")) {
                if (input.length() == 6) throw new MangoException("The index of the Task to unmark must be specified");
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task t = tasks.get(index);
                t.markAsNotDone();
                System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n   " +
                        t + "\n" + LINE);
            } else if (input.startsWith("todo")) {
                if (input.length() == 4) throw new MangoException("Todo description cannot be empty.");
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else if (input.startsWith("deadline")) {
                if (input.length() == 8) throw new MangoException("Deadline description cannot be empty.");
                String[] parts = input.substring(9).trim().split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                Task t = new Deadline(desc, by);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else if (input.startsWith("event")) {
                if (input.length() == 5) throw new MangoException("Event description cannot be empty.");
                String parts = input.substring(6).trim();
                int iFrom = parts.indexOf(" /from ");
                int iTo = parts.indexOf(" /to ", iFrom + 7);
                String desc = parts.substring(0, iFrom).trim();
                String from = parts.substring(iFrom + 7, iTo).trim();
                String to   = parts.substring(iTo + 5).trim();
                Task t = new Event(desc, from, to);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else if (input.startsWith("delete")) {
                if (input.length() == 6) throw new MangoException(
                        "The index of the Task to be deleted must be specified");
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                if (index < 0 || index + 1 > tasks.size()) throw new MangoException(
                        "The index of the Task to be deleted must be within the list.");
                Task removed = tasks.remove(index);
                System.out.println(
                        LINE + "\n Noted. I've removed this task:\n   " + removed +
                                "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE
                );
            } else {
                throw new MangoException("Invalid input.");
            }
        }
        sc.close();
    }
}
