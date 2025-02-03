package Command;

import TaskList.TaskList;
import UI.UI;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand implements Command {
    private final TaskList list;
    private final UI ui;

    /**
     * Constructs a new ListCommand.
     *
     * @param list the task list to be displayed.
     * @param ui   the user interface for displaying tasks.
     */
    public ListCommand(TaskList list, UI ui) {
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
        ui.displayTasks(list);
        return true;
    }
}
