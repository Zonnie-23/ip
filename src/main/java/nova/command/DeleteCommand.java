package nova.command;

import nova.exception.NovaException;
import nova.task.Task;
import nova.tasklist.TaskList;
import nova.ui.Ui;

/**
 * Command to delete a task from the task list
 */
public class DeleteCommand implements Command {
    private TaskList toDoList;
    private Ui ui;
    private int taskIndex;

    /**
     * Constructs a new delete command and verifies that the inputs are valid.
     *
     * @param toDoList    the task list to which the deadline will be added.
     * @param ui          the user interface for displaying messages.
     * @param instruction the command instruction containing the task description and deadline, parsed into an array
     *                    separated by spaces.
     * @throws NovaException if the instruction format is invalid or the deadline time cannot be parsed.
     */
    public DeleteCommand(TaskList toDoList, Ui ui, String[] instruction) throws NovaException {
        this.ui = ui;
        this.toDoList = toDoList;
        assert instruction.length <= 2;
        if (instruction.length < 2) {
            throw new NovaException("Please specify a task number to mark!");
        }
        if (!instruction[1].matches("\\d+")) {
            throw new NovaException("Task number must be an integer!");
        }
        this.taskIndex = Integer.parseInt(instruction[1]) - 1;
    }

    /**
     * Executes the delete command by removing the specified task from the task list.
     *
     * @return true if the task was removed successfully; false otherwise.
     */
    @Override
    public boolean execute() {
        try {
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                Task deletedTask = toDoList.removeTask(taskIndex);
                ui.addMessages("Noted. I've removed this task:", "  " + deletedTask);
                ui.addMessages(String.format("Now you have %d task(s) in the list.", toDoList.size()));
                return true;
            } else {
                throw new NovaException("Index is out of range!");
            }
        } catch (NovaException e) {
            ui.addMessages("Error: " + e.getMessage());
            return false;
        }
    }
}
