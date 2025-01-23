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
                System.out.println("    Bye. Hope to see you again soon!");
                isActive = false;
                break;
            case "LIST":
                System.out.println("    Here are the tasks in your list:\n");
                for (int i = 1; i <= toDoList.size(); i++) {
                    System.out.println("    " + i + "." + toDoList.get(i - 1));
                }
                break;
            case "MARK":
                try {
                    // Check if there exists a next input and is a integer
                    if (parts.length < 2) {
                        throw new NovaException("Please specify a task number to mark!");
                    }
                    if (!parts[1].matches("\\d+")) {
                        throw new NovaException("Task number must be an integer!");
                    }
                    int markIndex = Integer.parseInt(parts[1]) - 1;
                    if (markIndex >= 0 && markIndex < toDoList.size()) {
                        toDoList.get(markIndex).setStatus(true);
                        System.out.println("    Nice! I've marked this task as done:\n      " + toDoList.get(markIndex));
                    } else {
                        throw new NovaException("Index is out of range!");
                    }
                } catch(NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "UNMARK":
                try {
                    // Check if there exists a next input and is a integer
                    if (parts.length < 2) {
                        throw new NovaException("Please specify a task number to unmark!");
                    }
                    if (!parts[1].matches("\\d+")) {
                        throw new NovaException("Task number must be an integer!");
                    }
                    int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                    if (unmarkIndex >= 0 && unmarkIndex < toDoList.size()) {
                        toDoList.get(unmarkIndex).setStatus(false);
                        System.out.println("    OK, I've marked this task as not done yet:\n      "
                                + toDoList.get(unmarkIndex));
                    } else {
                        throw new NovaException("Index is out of range!");
                    }
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "EVENT":
                try {
                    String[] components = msg.split(" /from | /to ", 3);
                    Task event;
                    if (components.length != 3) {
                        throw new NovaException("Follow format event <event description> /from <time> /to <time>");
                    }
                    event = new Event(components[0].substring(parts[0].length()), components[1], components[2]);
                    toDoList.add(event);
                    System.out.println("    Got it. I've added this task:\n      " + event);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DEADLINE":
                try {
                    String[] elements = msg.split(" /by ",2);
                    Task deadline;
                    if (elements.length != 2) {
                        throw new NovaException("Follow format deadline <deadline description> /by <time>");
                    }
                    deadline = new Deadline(elements[0].substring(parts[0].length()), elements[1]);
                    toDoList.add(deadline);
                    System.out.println("    Got it. I've added this task:\n      " + deadline);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "TODO":
                try {
                    String desc = msg.substring(parts[0].length());
                    if (desc.isEmpty()) {
                        throw new NovaException("Follow format todo <todo description>");
                    }
                    Task todo = new Todo(desc);
                    toDoList.add(todo);
                    System.out.println("    Got it. I've added this task:\n      " + todo);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            default:
                try {
                    throw new NovaException("Sorry, I didn't understand your instructions. Please try again.");
                }  catch (NovaException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            System.out.println(HORIZONTAL_BAR);
        }
    }
}
