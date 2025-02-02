package Command;

import Task.Task;
import TaskList.TaskList;
import UI.UI;

import java.util.List;

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
