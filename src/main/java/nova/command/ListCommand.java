package nova.command;

import nova.tasklist.TaskList;
import nova.ui.Ui;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand implements Command {
    private final TaskList list;
    private final Ui ui;

    /**
     * Constructs a new ListCommand.
     *
     * @param list the task list to be displayed.
     * @param ui   the user interface for displaying tasks.
     */
    public ListCommand(TaskList list, Ui ui) {
        this.list = list;
        this.ui = ui;
    }

    /**
     * Executes the list command by displaying all tasks in the task list.
     *
     * @return true if tasks were displayed successfully.
     */
    @Override
    public boolean execute() {
        ui.addMessages("Here are the tasks in your list: ");
        ui.displayTasks(list);
        return true;
    }
}
