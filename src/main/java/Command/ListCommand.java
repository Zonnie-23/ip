package Command;

import TaskList.TaskList;
import Ui.Ui;

public class ListCommand implements Command {
    private final TaskList list;
    private final Ui ui;

    public ListCommand(TaskList list, Ui ui) {
        this.list = list;
        this.ui = ui;
    }

    @Override
    public boolean execute() {
        ui.displayTasks(list);
        return true;
    }
}
