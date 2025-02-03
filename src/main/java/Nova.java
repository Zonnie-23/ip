import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Command.ByeCommand;
import Command.Command;
import Command.DeadlineCommand;
import Command.DeleteCommand;
import Command.EventCommand;
import Command.ListCommand;
import Command.StatusUpdateCommand;
import Command.TodoCommand;
import Exception.NovaException;
import Storage.Storage;
import TaskList.TaskList;
import Ui.Ui;

public class Nova {
    private static final List<String> COMMANDS = Arrays.asList("bye", "list", "mark", "unmark", "event", "deadline", "todo", "delete");

    public static void main(String[] args) {
        // Initialise to-do list
        Storage taskDataManager = new Storage("./data/task.csv");
        TaskList toDoList = new TaskList(taskDataManager.loadTask());

        // Greet User
        Ui ui = new Ui();
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
            case "HELP":
                ui.displayMessages("I accept the following instructions:", COMMANDS.toString());
                isSuccessful = true;
                break;
            case "LIST":
                Command listCommand = new ListCommand(toDoList, ui);
                isSuccessful = listCommand.execute();
                break;
            case "TODO":
                try {
                    Command todoCommand = new TodoCommand(toDoList, ui, msg);
                    isSuccessful = todoCommand.execute();
                } catch (NovaException e) {
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DEADLINE":
                try {
                    Command deadlineCommand = new DeadlineCommand(toDoList, ui, msg);
                    isSuccessful = deadlineCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "EVENT":
                try {
                    Command eventCommand = new EventCommand(toDoList, ui, msg);
                    isSuccessful = eventCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "MARK":
                shouldMark = true;
            case "UNMARK":
                try {
                    Command markCommand = new StatusUpdateCommand(toDoList, ui, msgParts, shouldMark);
                    isSuccessful = markCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "DELETE":
                try {
                    Command deleteCommand = new DeleteCommand(toDoList, ui, msgParts);
                    isSuccessful = deleteCommand.execute();
                } catch (NovaException e){
                    System.out.println("    Error: " + e.getMessage());
                }
                break;
            case "BYE":
                Command byeCommand = new ByeCommand(toDoList, ui, taskDataManager, scanner);
                isSuccessful = byeCommand.execute();
                isActive = false;
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
