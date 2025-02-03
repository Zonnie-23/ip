package Command;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import Exception.NovaException;
import Parser.Parser;
import Task.Deadline;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

/**
 * Command to add a deadline task to the task list
 */
public class DeadlineCommand implements Command {
    private final TaskList toDoList;
    private final Ui ui;
    private final String[] msgParts;
    private final LocalDateTime deadlineTime;

    /**
     * Constructs a new deadline command and verifies that the inputs are valid.
     *
     * @param toDoList    the task list to which the deadline will be added.
     * @param ui          the user interface for displaying messages.
     * @param instruction the command instruction containing the task description and deadline.
     * @throws NovaException if the instruction format is invalid or the deadline time cannot be parsed.
     */
    public DeadlineCommand(TaskList toDoList, Ui ui, String instruction) throws NovaException {
        this.toDoList = toDoList;
        this.ui = ui;
        this.msgParts = instruction.split(" /by ",2);
        if (msgParts.length != 2) {
            throw new NovaException("Follow format: deadline <deadline description> /by <time>");
        }
        try {
            this.deadlineTime = Parser.parseDateTime(msgParts[1]);
        } catch (DateTimeException e) {
            throw new NovaException("Invalid deadline time: " + e.getMessage());
        }

    }

    /**
     * Executes the deadline command by creating a new deadline task and adding it to the toDoList
     *
     * @return true if the task was added successfully, false otherwise
     */
    @Override
    public boolean execute() {
        Task event = new Deadline(msgParts[0].substring("deadline".length() + 1), deadlineTime);
        toDoList.addTask(event);
        ui.displayMessages("Got it. I've added this task:" , "  " + event);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
