import Command.ByeCommand;
import Command.Command;
import Command.ListCommand;
import Command.StatusUpdateCommand;
import Command.DeleteCommand;
import Exception.NovaException;
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
        UI ui = new UI();
        ui.displayMessage("Hello! I'm Nova.", "What can I do for you?");
        ui.close();

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        boolean isSuccessful = false;

        while (isActive) {
            String msg = scanner.nextLine();
            String[] parts = msg.split("\\s+");
            boolean shouldMark = false;
            ui.open();

            switch (parts[0].toUpperCase()) {
            case "BYE":
                Command byeCommand = new ByeCommand(toDoList, taskDataManager, scanner, ui);
                isSuccessful = byeCommand.execute();
                isActive = false;
                break;
            case "LIST":
                Command listCommand = new ListCommand(toDoList, ui);
                isSuccessful = listCommand.execute();
                break;
            case "MARK":
                shouldMark = true;
            case "UNMARK":
                try {
                    Command markCommand = new StatusUpdateCommand(toDoList, parts, ui, shouldMark);
                    isSuccessful = markCommand.execute();
                } catch (NovaException e){
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
                    Command deleteCommand = new DeleteCommand(toDoList, parts, ui);
                    isSuccessful = deleteCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "HELP":
                ui.displayMessage("I accept the following instructions:", COMMANDS.toString());
                isSuccessful = true;
                break;
            default:
                try {
                    ui.displayMessage("Sorry, I didn't understand your instructions. Please try again.");
                    throw new NovaException("Type help for list of commands.");
                }  catch (NovaException e) {
                    System.out.println("    " + e.getMessage());
                    // Default clause is meant to handle any unknown command, so if we reach this clause, then the handling
                    // of the unknown instruction is successful
                    isSuccessful = true;
                }
            }
            if (!isSuccessful) {
                // To inform user that command is found but execution is unsuccessful
                ui.displayMessage("Please try again.");
            }
            ui.close();
        }

        scanner.close();
    }
}
