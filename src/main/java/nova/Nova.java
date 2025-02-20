package nova;

import java.util.Arrays;
import java.util.List;

import nova.command.ByeCommand;
import nova.command.Command;
import nova.command.DeadlineCommand;
import nova.command.DeleteCommand;
import nova.command.EventCommand;
import nova.command.ExitCommand;
import nova.command.FindCommand;
import nova.command.HelpCommand;
import nova.command.ListCommand;
import nova.command.SaveCommand;
import nova.command.ScheduleCommand;
import nova.command.StatusUpdateCommand;
import nova.command.TodayCommand;
import nova.command.TodoCommand;
import nova.exception.NovaException;
import nova.storage.Storage;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class Nova {
    private static final List<String> COMMANDS =
            Arrays.asList("deadline", "event", "todo", "list", "find", "schedule", "mark", "unmark", "delete",
                    "save", "bye" );

    private Ui ui = new Ui();
    private Storage taskDataManager = new Storage("./data/task.csv", ui);
    private TaskList toDoList = new TaskList(taskDataManager.loadTask());
    private boolean isActive = true;
    private Command currCommand;

    private Boolean read(String command) {
        try {
            currCommand = parseCommand(command);
            boolean isSuccessful = currCommand.execute();
            if (!isSuccessful) {
                ui.addMessages("Please try again.");
            }

            // Special handling for exiting if needed
            if (currCommand instanceof SaveCommand) {
                isActive = !((SaveCommand) currCommand).isExiting();
                if (!isActive) {
                    ui.addMessages("Hope to see you again soon");
                }
            } else if (currCommand instanceof ExitCommand) {
                // When an ExitCommand is executed, we set isActive to false
                isActive = false;
            }
        } catch (NovaException e) {
            ui.addMessages("Error: " + e.getMessage());
        }
        return true;
    }

    private Command parseCommand(String command) throws NovaException {
        String[] msgParts = command.split("\\s+");
        String cmdWord = msgParts[0];
        switch (cmdWord) {
        case "help":
            return new HelpCommand(ui, COMMANDS); // for example, a new command for help
        case "list":
            return new ListCommand(toDoList, ui);
        case "find":
            return new FindCommand(toDoList, ui, command);
        case "schedule":
            return new ScheduleCommand(ui, toDoList, command);

        case "todo":
            return new TodoCommand(toDoList, ui, command);
        case "deadline":
            return new DeadlineCommand(toDoList, ui, command);
        case "event":
            return new EventCommand(toDoList, ui, command);

        case "mark":
            return new StatusUpdateCommand(toDoList, ui, msgParts, true);
        case "unmark":
            return new StatusUpdateCommand(toDoList, ui, msgParts, false);
        case "delete":
            return new DeleteCommand(toDoList, ui, msgParts);

        case "save":
            boolean shouldExit = currCommand instanceof ByeCommand;
            return new SaveCommand(toDoList, ui, taskDataManager, shouldExit);
        case "bye":
            return new ByeCommand(ui);
        default:
            // Special handling: if the last command was BYE and user enters "no"
            if (currCommand instanceof ByeCommand && cmdWord.equalsIgnoreCase("no")) {
                return new ExitCommand(ui); // a command that handles exit logic
            }
            ui.addMessages("Sorry, I didn't understand your instructions.", "Please try again.");
            throw new NovaException("Type \"help\" for list of commands.");
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        this.ui.reinitialiseResponse();
        Boolean hasProcessed = read(input);
        return ui.generateResponse();
    }

    public String[] getInitialMessage() {
        this.ui.addMessages("Hello! I'm Nova.");
        try {
            currCommand = new TodayCommand(ui, toDoList);
            this.currCommand.execute();
        } catch (Exception e) {
            ui.addMessages("Unable to display tasks for today.");
        }
        return new String[]{ui.generateResponse(), "What can I do for you?"};
    }

    public boolean isActive() {
        return isActive;
    }
}
