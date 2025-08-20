import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MangoBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> tasks = new ArrayList<>();
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
            } else {
                tasks.add(input);
                System.out.println("____________________________________________________________\n" +
                        " added: " + input +
                        "\n____________________________________________________________");
            }
        }
        sc.close();
    }
}
