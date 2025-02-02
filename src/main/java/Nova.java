import Command.ByeCommand;
import Command.Command;
import Command.DeadlineCommand;
import Command.DeleteCommand;
import Command.EventCommand;
import Command.ListCommand;
import Command.StatusUpdateCommand;
import Exception.NovaException;
import Storage.Storage;
import Task.Task;
import Task.Todo;
import UI.UI;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final List<String> COMMANDS = Arrays.asList("bye", "list", "mark", "unmark", "event", "deadline", "todo", "delete");

    public static void main(String[] args) {
        // Initialise to-do list
        Storage taskDataManager = new Storage("./data/task.txt");
        List<Task> toDoList = taskDataManager.loadTask();

        // Greet User
        UI ui = new UI();
        ui.open();
        ui.displayMessages("Hello! I'm Nova.", "What can I do for you?");
        ui.close();

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;

        while (isActive) {
            String msg = scanner.nextLine();
            String[] msgParts = msg.split("\\s+");
            boolean shouldMark = false;
            boolean isSuccessful = false;
            ui.open();

            switch (msgParts[0].toUpperCase()) {
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
                    Command markCommand = new StatusUpdateCommand(toDoList, msgParts, ui, shouldMark);
                    isSuccessful = markCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "EVENT":
                try {
                    Command eventCommand = new EventCommand(msg, toDoList, ui);
                    isSuccessful = eventCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DEADLINE":
                try {
                    Command deadlineCommand = new DeadlineCommand(msg, toDoList, ui);
                    isSuccessful = deadlineCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "TODO":
                try {
                    String desc = msg.substring(msgParts[0].length() + 1);
                    if (desc.isEmpty()) {
                        throw new NovaException("Follow format: todo <todo description>");
                    }
                    Task todo = new Todo(desc);
                    toDoList.add(todo);
                    System.out.println("    Got it. I've added this task:\n      " + todo);
                    System.out.println(String.format("    Now you have %d tasks in the list.", toDoList.size()));
                    isSuccessful = true;
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DELETE":
                try {
                    Command deleteCommand = new DeleteCommand(toDoList, msgParts, ui);
                    isSuccessful = deleteCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "HELP":
                ui.displayMessages("I accept the following instructions:", COMMANDS.toString());
                isSuccessful = true;
                break;
            default:
                try {
                    ui.displayMessages("Sorry, I didn't understand your instructions. Please try again.");
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
                ui.displayMessages("Please try again.");
            }
            ui.close();
        }

        scanner.close();
    }
}
