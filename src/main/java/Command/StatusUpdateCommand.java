package Command;

import Exception.NovaException;
import TaskList.TaskList;
import UI.UI;

public class StatusUpdateCommand implements Command {
    private TaskList toDoList;
    private UI ui;
    private int taskIndex;
    private boolean isDone;

    public StatusUpdateCommand(TaskList toDoList, UI ui, String[] instruction , boolean isDone) throws NovaException {
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
                toDoList.getTask(taskIndex).setStatus(isDone);
                ui.displayMessages("Nice! I've marked this task as done:", "  " + toDoList.getTask(taskIndex).toString());
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
