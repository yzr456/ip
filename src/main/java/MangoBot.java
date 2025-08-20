import java.util.Scanner;

public class MangoBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
            }
            System.out.println("____________________________________________________________\n " +
                    input +
                    "\n____________________________________________________________");
        }
        sc.close();
    }
}
