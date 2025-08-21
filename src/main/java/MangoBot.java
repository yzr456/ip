import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MangoBot {
    private static final String LINE = "____________________________________________________________";
    public static void main(String[] args) {
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
                    System.out.println("");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task t = tasks.get(index);
                t.markAsDone();
                System.out.println(LINE + "\n Nice! I've marked this task as done:\n   " +
                        t + "\n" + LINE);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task t = tasks.get(index);
                t.markAsNotDone();
                System.out.println(LINE + "\n OK, I've marked this task as not done yet:\n   " +
                        t + "\n" + LINE);
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).trim().split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                Task t = new Deadline(desc, by);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else if (input.startsWith("event ")) {
                String parts = input.substring(6).trim();
                int iFrom = parts.indexOf(" /from ");
                int iTo   = parts.indexOf(" /to ", iFrom + 7);
                String desc = parts.substring(0, iFrom).trim();
                String from = parts.substring(iFrom + 7, iTo).trim();
                String to   = parts.substring(iTo + 5).trim();
                Task t = new Event(desc, from, to);
                tasks.add(t);
                System.out.println(LINE + "\n Got it. I've added this task:\n   " + t +
                        "\n Now you have " + tasks.size() + " tasks in the list.\n" + LINE);
            } else {
                System.out.println(LINE + input + LINE);
            }
        }
        sc.close();
    }
}
