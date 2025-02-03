package Command;

import Exception.NovaException;
import Task.Task;
import Task.Todo;
import TaskList.TaskList;
import UI.UI;

public class TodoCommand implements Command{
    private TaskList toDoList;
    private UI ui;
    private String description;

    public TodoCommand(TaskList toDoList, UI ui, String instruction) throws NovaException {
        this.toDoList = toDoList;
        this.ui = ui;
        this.description = instruction.substring("todo".length() + 1);
        if (this.description.isEmpty()) {
            throw new NovaException("Follow format: todo <todo description>");
        }
    }

    @Override
    public boolean execute() {
        Task todo = new Todo(this.description);
        toDoList.addTask(todo);
        ui.displayMessages("Got it. I've added this task:" , "  " + todo);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
