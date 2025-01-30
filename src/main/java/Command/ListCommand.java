package Command;

import Task.Task;
import UI.UI;

import java.util.List;

public class ListCommand implements Command {
    private List<Task> list;

    public ListCommand(List<Task> list) {
        this.list = list;
    }

    @Override
    public boolean execute() {
        UI.displayTaskList(list);
        return true;
    }
}
