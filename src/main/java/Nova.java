import Command.ByeCommand;
import Command.Command;
import Command.ListCommand;
import Storage.Storage;
import Task.Deadline;
import Task.Event;
import Task.Task;
import Task.Todo;
import UI.UI;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________\n";
    private static final List<String> COMMANDS = Arrays.asList("bye", "list", "mark", "unmark", "event", "deadline", "todo", "delete");

    public static void main(String[] args) {
        // Initialise to-do list
        Storage taskDataManager = new Storage("./data/task.txt");
        List<Task> toDoList = taskDataManager.loadTask();

        // Greet User
        UI.displayCompleteMessage("Hello! I'm Nova.", "What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        boolean isSuccesful = false;

        while (isActive) {
            String msg = scanner.nextLine();
            String[] parts = msg.split("\\s+");

            switch (parts[0].toUpperCase()) {
            case "BYE":
                Command byeCommand = new ByeCommand(toDoList, taskDataManager, scanner);
                isSuccesful = byeCommand.execute();
                isActive = false;
                break;
            case "LIST":
                Command listCommand = new ListCommand(toDoList);
                isSuccesful = listCommand.execute();
                break;
            case "MARK":
                try {
                    // Check if there exists a next input and is a integer
                    if (parts.length < 2) {
                        throw new NovaException("Please specify a task number to mark!");
                    }
                    if (!parts[1].matches("\\d+")) {
                        throw new NovaException("Task.Task number must be an integer!");
                    }
                    int markIndex = Integer.parseInt(parts[1]) - 1;
                    if (markIndex >= 0 && markIndex < toDoList.size()) {
                        toDoList.get(markIndex).setStatus(true);
                        System.out.println("    Nice! I've marked this task as done:\n      " + toDoList.get(markIndex));
                    } else {
                        throw new NovaException("Index is out of range!");
                    }
                } catch (NovaException e){
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
                        throw new NovaException("Task.Task number must be an integer!");
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
                        throw new NovaException("Follow format: event <event description> /from <time> /to <time>");
                    }
                    event = new Event(components[0].substring(parts[0].length() + 1), components[1], components[2]);
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
                        throw new NovaException("Follow format: deadline <deadline description> /by <time>");
                    }
                    deadline = new Deadline(elements[0].substring(parts[0].length() + 1), elements[1]);
                    toDoList.add(deadline);
                    System.out.println("    Got it. I've added this task:\n      " + deadline);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "TODO":
                try {
                    String desc = msg.substring(parts[0].length() + 1);
                    if (desc.isEmpty()) {
                        throw new NovaException("Follow format: todo <todo description>");
                    }
                    Task todo = new Todo(desc);
                    toDoList.add(todo);
                    System.out.println("    Got it. I've added this task:\n      " + todo);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DELETE":
                try {
                    // Check if there exists a next input and is a integer
                    if (parts.length < 2) {
                        throw new NovaException("Please specify a task number to delete!");
                    }
                    if (!parts[1].matches("\\d+")) {
                        throw new NovaException("Task.Task number must be an integer!");
                    }
                    int delIndex = Integer.parseInt(parts[1]) - 1;
                    if (delIndex >= 0 && delIndex < toDoList.size()) {
                        Task deletedTask = toDoList.remove(delIndex);
                        System.out.println("    Noted. I've removed this task:\n      " + deletedTask);
                        System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                    } else {
                        throw new NovaException("Index is out of range!");
                    }
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "HELP":
                System.out.println("    I accept the following instructions: \n    " + COMMANDS.toString());
                break;
            default:
                try {
                    System.out.println("    Sorry, I didn't understand your instructions. Please try again.\n");
                    throw new NovaException("Type help for list of commands.");
                }  catch (NovaException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
        }
        if (!isSuccesful) {

        }
        scanner.close();
    }
}
