package Command;

import Task.Task;
import UI.UI;

import java.util.List;

public class ListCommand implements Command {
    private final List<Task> list;
    private final UI ui;

    public ListCommand(List<Task> list, UI ui) {
        this.list = list;
        this.ui = ui;
    }

    @Override
    public boolean execute() {
        ui.displayTaskList(list);
        return true;
    }
}
