package Command;

import Task.Task;
import TaskList.TaskList;
import UI.UI;
import Exception.NovaException;

import java.util.List;

public class DeleteCommand implements Command {
    private TaskList toDoList;
    private UI ui;
    private int taskIndex;


    public DeleteCommand(TaskList toDoList, String[] instruction, UI ui) throws NovaException {
        this.ui = ui;
        this.toDoList = toDoList;
        if (instruction.length < 2) {
            throw new NovaException("Please specify a task number to mark!");
        }
        if (!instruction[1].matches("\\d+")) {
            throw new NovaException("Task number must be an integer!");
        }
        this.taskIndex = Integer.parseInt(instruction[1]) - 1;
    }

    @Override
    public boolean execute() {
        try {
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                Task deletedTask = toDoList.removeTask(taskIndex);
                ui.displayMessages("Noted. I've removed this task:", "  " + deletedTask);
                ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
                return true;
            } else {
                throw new NovaException("Index is out of range!");
            }
        } catch (NovaException e) {
            System.out.println("    Error: " + e.getMessage());
            return false;
            }
    }
}
