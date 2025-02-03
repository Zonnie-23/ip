package Command;

import java.util.Scanner;

import Storage.Storage;
import TaskList.TaskList;
import Ui.Ui;

public class ByeCommand implements Command {
    private final TaskList toDoList;
    private final Ui ui;
    private final Storage storage;
    private final Scanner scanner;

    /**
     * Constructs a new ByeCommand instance.
     * <p>
     * This constructor initializes the command with the necessary dependencies to handle the application's exit process.
     * It accepts a task list (representing the current state of tasks), a user interface for message display,
     * a storage handler for saving tasks, and a scanner for reading user input.
     * </p>
     *
     * @param toDoList the current task list to be potentially saved upon exit.
     * @param ui       the user interface used to display messages and prompts to the user.
     * @param storage  the storage handler responsible for persisting the task list.
     * @param scanner  the scanner instance used to capture user input.
     */
    public ByeCommand(TaskList toDoList, Ui ui, Storage storage, Scanner scanner) {
        this.toDoList = toDoList;
        this.ui = ui;
        this.storage = storage;
        this.scanner = scanner;
    }

    /**
     * Executes the bye command.
     * <p>
     * The method displays a prompt asking whether the user wants to save their todo list.
     * If the user responds with an input starting with "y", it attempts to save the task list.
     * A success or failure message is then displayed based on the outcome of the save operation.
     * If the user declines, a farewell message is shown.
     * </p>
     *
     * @return {@code true} if the command executed successfully; {@code false} if the save operation fails.
     */
    @Override
    public boolean execute() {
        ui.displayMessages("Do you want to save your todo list? Type \"yes\" to save");
        if (scanner.nextLine().startsWith("y")) {
            boolean status = storage.saveTask(toDoList);
            if (status) {
                ui.displayMessages("Your file has been saved. Hope to see you again soon!");
            } else {
                ui.displayMessages("File not saved!");
                return false;
            }
        } else {
            ui.displayMessages("Bye. Hope to see you again soon!");
        }
        return true;
    }
}
