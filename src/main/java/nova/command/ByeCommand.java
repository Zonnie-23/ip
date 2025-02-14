package nova.command;

import nova.ui.Ui;

public class ByeCommand implements Command {
    private final Ui ui;

    /**
     * Constructs a new ByeCommand instance.
     * <p>
     * This constructor initializes the command with necessary dependencies to handle the application's exit process.
     * It accepts a task list (representing the current state of tasks), a user interface for message display,
     * a storage handler for saving tasks, and a scanner for reading user input.
     * </p>
     *
     * @param ui       the user interface used to display messages and prompts to the user.
     */
    public ByeCommand(Ui ui) {
        this.ui = ui;
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
        ui.addMessages("Do you want to save? Type \"save\" to save the current list. Otherwise, type \"no\" to quit.");
        return true;
    }
}
