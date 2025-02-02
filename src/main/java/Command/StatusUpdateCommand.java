package Command;

import Task.Task;
import Exception.NovaException;
import UI.UI;

import java.util.List;

public class StatusUpdateCommand implements Command {
    private List<Task> toDoList;
    private int taskIndex;
    private UI ui;
    private boolean isDone;

    public StatusUpdateCommand(List<Task> toDoList, String[] instruction, UI ui, boolean isDone) throws NovaException {
        this.ui = ui;
        this.toDoList = toDoList;
        if (instruction.length < 2) {
            throw new NovaException("Please specify a task number to mark!");
        }
        if (!instruction[1].matches("\\d+")) {
            throw new NovaException("Task number must be an integer!");
        }
        this.taskIndex = Integer.parseInt(instruction[1]) - 1;
        this.isDone = isDone;
    }

    @Override
    public boolean execute() {
        try {
            // Check if there exists a next input and is a integer
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                toDoList.get(taskIndex).setStatus(isDone);
                ui.displayMessages("Nice! I've marked this task as done:", "  " + toDoList.get(taskIndex).toString());
            } else {
                throw new NovaException("Index is out of range!");
            }
            return true;
        } catch (NovaException e){
            System.out.println("    Error: " + e.getMessage());
            return false;
        }
    }
}
