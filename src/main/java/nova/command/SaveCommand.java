package nova.command;

import nova.storage.Storage;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class SaveCommand implements Command {
    private final TaskList toDoList;
    private final Ui ui;
    private final Storage storage;

    /**
     * Constructs a new ByeCommand instance.
     * <p>
     * This constructor initializes the command with necessary dependencies to handle the application's exit process.
     * It accepts a task list (representing the current state of tasks), a user interface for message display,
     * a storage handler for saving tasks, and a scanner for reading user input.
     * </p>
     *
     * @param toDoList the current task list to be potentially saved upon exit.
     * @param ui       the user interface used to display messages and prompts to the user.
     * @param storage  the storage handler responsible for persisting the task list.
     */
    public SaveCommand(TaskList toDoList, Ui ui, Storage storage) {
        this.toDoList = toDoList;
        this.ui = ui;
        this.storage = storage;
    }

    public boolean execute() {
        boolean status = storage.saveTask(toDoList);
        if (status) {
            ui.addMessages("Your file has been saved. Hope to see you again soon!");
            return true;
        } else {
            ui.addMessages("File not saved!");
            return false;
        }
    }
}
