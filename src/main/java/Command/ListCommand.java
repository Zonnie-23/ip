package Command;

import TaskList.TaskList;
import UI.UI;

public class ListCommand implements Command {
    private final TaskList list;
    private final UI ui;

    public ListCommand(TaskList list, UI ui) {
        this.list = list;
        this.ui = ui;
    }

    @Override
    public boolean execute() {
        ui.displayTasks(list);
        return true;
    }
}
