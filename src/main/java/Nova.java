import java.util.Scanner;
import java.util.ArrayList;

public class Nova {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________\n";

    public static void main(String[] args) {
        // Initialise to-do list
        ArrayList<Task> toDoList = new ArrayList<Task>();

        // Greet User
        System.out.println(HORIZONTAL_BAR
                + "    Hello! I'm Nova.\n"
                + "    What can I do for you?\n"
                + HORIZONTAL_BAR);

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;

        while (isActive) {
            String msg = scanner.nextLine();
            System.out.println(HORIZONTAL_BAR);
            switch (msg.toUpperCase()) {
            case "BYE":
                msg = "Bye. Hope to see you again soon!\n";
                isActive = false;
                break;
            case "LIST":
                msg = "";
                for (int i = 1; i <= toDoList.size(); i++) {
                    msg += i + ". " + toDoList.get(i-1) + "\n" + "    " ;
                }
                break;
            default:
                toDoList.add(new Task(msg));
                msg = "added: " + msg;
            }
            System.out.println("    " + msg + "\n" + HORIZONTAL_BAR);
        }
    }
}
