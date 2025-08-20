import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MangoBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        System.out.println("____________________________________________________________\n" +
                " Hello! I'm MangoBot\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n");
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (tasks.isEmpty()) {
                    System.out.println("");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task t = tasks.get(index);
                t.markAsDone();
                System.out.println("____________________________________________________________\n" +
                        "Nice! I've marked this task as done:\n" +
                        " " + t +
                        "\n____________________________________________________________");
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task t = tasks.get(index);
                t.markAsNotDone();
                System.out.println("____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n" +
                        " " + t +
                        "\n____________________________________________________________");
            } else {
                tasks.add(new Task(input));
                System.out.println("____________________________________________________________\n" +
                        " added: " + input +
                        "\n____________________________________________________________");
            }
        }
        sc.close();
    }
}
