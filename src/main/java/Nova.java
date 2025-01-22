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
            String[] parts = msg.split("\\s+");

            switch (parts[0].toUpperCase()) {
            case "BYE":
                System.out.println("    Bye. Hope to see you again soon!\n");
                isActive = false;
                break;
            case "LIST":
                System.out.println("    Here are the tasks in your list:\n");
                for (int i = 1; i <= toDoList.size(); i++) {
                    System.out.println("    " + i + "." + toDoList.get(i - 1));
                }
                break;
            case "MARK":
                // Check if there exists a next input and is a integer
                if (parts.length < 2) {
                    System.out.println("    Error: Please specify a task number to mark!\n");
                } else if (!parts[1].matches("\\d+")) {
                    System.out.println("    Error: Task number must be an integer!\n");
                } else {
                    int markIndex = Integer.parseInt(parts[1]) - 1;
                    if (markIndex >= 0 && markIndex < toDoList.size()) {
                        toDoList.get(markIndex).setStatus(true);
                        System.out.println("    Nice! I've marked this task as done:\n" + toDoList.get(markIndex));
                    } else {
                        System.out.println("    Index is out of range!\n");
                    }
                }
                break;
            case "UNMARK":
                // Check if there exists a next input and is a integer
                if (parts.length < 2) {
                    System.out.println("    Error: Please specify a task number to mark!\n");
                } else if (!parts[1].matches("\\d+")) {
                    System.out.println("    Error: Task number must be an integer!\n");
                } else {
                    int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                    if (unmarkIndex >= 0 && unmarkIndex < toDoList.size()) {
                        toDoList.get(unmarkIndex).setStatus(false);
                        System.out.println("    OK, I've marked this task as not done yet:\n    " + toDoList.get(unmarkIndex));
                    } else {
                        System.out.println("    Index is out of range!\n");
                        ;
                    }
                }
                break;
            default:
                toDoList.add(new Task(msg));
                System.out.println("    added: " + msg);
            }
            System.out.println(HORIZONTAL_BAR);
        }
    }
}
