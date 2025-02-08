package nova;

import java.util.Arrays;
import java.util.List;

import nova.command.ByeCommand;
import nova.command.Command;
import nova.command.DeadlineCommand;
import nova.command.DeleteCommand;
import nova.command.EventCommand;
import nova.command.FindCommand;
import nova.command.ListCommand;
import nova.command.SaveCommand;
import nova.command.StatusUpdateCommand;
import nova.command.TodoCommand;
import nova.exception.NovaException;
import nova.storage.Storage;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class Nova {
    private static final List<String> COMMANDS =
            Arrays.asList("bye", "list", "mark", "unmark", "event", "deadline", "todo", "delete");

    private Storage taskDataManager = new Storage("./data/task.csv");
    private TaskList toDoList = new TaskList(taskDataManager.loadTask());
    private Ui ui = new Ui();
    private boolean isActive = true;
    private Command currCommand;

    private Boolean read(String command) {
        String[] msgParts = command.split("\\s+");
        boolean shouldMark = false;
        boolean isSuccessful = false;

        switch (msgParts[0].toUpperCase()) {
        case "HELP":
            ui.addMessages("I accept the following instructions:", COMMANDS.toString());
            isSuccessful = true;
            break;
        case "LIST":
            Command listCommand = new ListCommand(toDoList, ui);
            isSuccessful = listCommand.execute();
            break;
        case "FIND":
            Command findCommand = new FindCommand(toDoList, ui, command);
            isSuccessful = findCommand.execute();
            break;
        case "TODO":
            try {
                Command todoCommand = new TodoCommand(toDoList, ui, command);
                isSuccessful = todoCommand.execute();
            } catch (NovaException e) {
                ui.addMessages("Error: " + e.getMessage());
            }
            break;
        case "DEADLINE":
            try {
                Command deadlineCommand = new DeadlineCommand(toDoList, ui, command);
                isSuccessful = deadlineCommand.execute();
            } catch (NovaException e) {
                ui.addMessages("Error: " + e.getMessage());
            }
            break;
        case "EVENT":
            try {
                Command eventCommand = new EventCommand(toDoList, ui, command);
                isSuccessful = eventCommand.execute();
            } catch (NovaException e) {
                ui.addMessages("Error: " + e.getMessage());
            }
            break;
        case "MARK":
            shouldMark = true;
            // Fallthrough
        case "UNMARK":
            try {
                Command markCommand = new StatusUpdateCommand(toDoList, ui, msgParts, shouldMark);
                isSuccessful = markCommand.execute();
            } catch (NovaException e) {
                ui.addMessages("Error: " + e.getMessage());
            }
            break;
        case "DELETE":
            try {
                Command deleteCommand = new DeleteCommand(toDoList, ui, msgParts);
                isSuccessful = deleteCommand.execute();
            } catch (NovaException e) {
                ui.addMessages("Error: " + e.getMessage());
            }
            break;
        case "BYE":
            currCommand = new ByeCommand(ui);
            isSuccessful = currCommand.execute();
            break;
        default:
            // To check if the command is a response to byeCommand checking if user wants to save
            if (currCommand != null && currCommand instanceof ByeCommand) {
                if (!msgParts[0].equalsIgnoreCase("no")) {
                    currCommand = new SaveCommand(toDoList, ui, taskDataManager);
                    isSuccessful = currCommand.execute();
                }
                ui.addMessages("Bye. Hope to see you again soon!");
                this.isActive = false;
                break;
            }
            try {
                ui.addMessages("Sorry, I didn't understand your instructions. Please try again.");
                throw new NovaException("Type help for list of commands.");
            } catch (NovaException e) {
                ui.addMessages(e.getMessage());
                // Default clause is meant to handle any unknown command, so if we reach this clause,
                // then the handling of the unknown instruction is successful
                isSuccessful = true;
            }
        }
        if (!isSuccessful) {
            // To inform user that command is found but execution is unsuccessful
            ui.addMessages("Please try again.");
        }
        return true;
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        this.ui.reinitialiseResponse();
        Boolean hasProcessed = read(input);
        return ui.generateResponse();
    }

    public String getInitialMessage() {
        return "Hello! I'm Nova.\nWhat can I do for you?";
    }

    public boolean isActive() {
        return isActive;
    }
}
