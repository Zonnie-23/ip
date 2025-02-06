package command;

import exception.NovaException;
import tasklist.TaskList;
import ui.Ui;

/**
 *
 * Command for updating the completion status of a specific task in the task list.
 * <p>
 * This command modifies the status of a task (marking it as done or not done) based on a user-provided task number.
 * The task to update is identified via an instruction array, where the second element represents the task number.
 * </p>
 */
public class StatusUpdateCommand implements Command {
    private TaskList toDoList;
    private Ui ui;
    private int taskIndex;
    private boolean isDone;

    /**
     * Constructs a new StatusUpdateCommand.
     * <p>
     * This constructor initializes the command with the necessary components to update a task's status.
     * The {@code instruction} parameter is expected to contain three elements; the second element should be the
     * task number (as a string) that identifies the task to update. The {@code isDone} flag indicates whether the task
     * should be marked as complete (true) or incomplete (false).
     * </p>
     *
     * @param toDoList   the task list containing the tasks to be updated.
     * @param ui         the user interface used for displaying messages to the user.
     * @param instruction an array of strings representing the command details; the second element must be a valid task number.
     * @param isDone     the new status for the task; {@code true} to mark as done, {@code false} to mark as not done.
     * @throws NovaException if the instruction does not provide a valid task number or if the task number is not a valid integer.
     */
    public StatusUpdateCommand(TaskList toDoList, Ui ui, String[] instruction , boolean isDone) throws NovaException {
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

    /**
     * Executes the status update command.
     * <p>
     * This method attempts to update the status of the task identified by the provided task number.
     * If the task index is valid, the task's status is updated and a confirmation message is displayed.
     * If the task index is out of range, an error message is printed.
     * </p>
     *
     * @return {@code true} if the task status was updated successfully; {@code false} otherwise.
     */
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
