package nova.command;

import java.time.LocalDateTime;

import nova.exception.NovaException;
import nova.parser.Parser;
import nova.task.Deadline;
import nova.task.Task;
import nova.tasklist.TaskList;
import nova.ui.Ui;

/**
 * Command to add a deadline task to the task list
 */
public class DeadlineCommand implements Command {
    private final TaskList toDoList;
    private final Ui ui;
    private final String[] instruction;
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
        this.instruction = instruction.split(" /by ", 2);
        if (this.instruction.length != 2) {
            throw new NovaException("Follow format: deadline <deadline description> /by <time>");
        }
        assert this.instruction[0].startsWith("deadline");
        try {
            this.deadlineTime = Parser.parseDateTime(this.instruction[1]);
        } catch (NovaException e) {
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
        Task event;
        try {
            event = new Deadline(instruction[0].substring("deadline".length() + 1), deadlineTime);
        } catch (IndexOutOfBoundsException e) {
            ui.addMessages("Unable to parse deadline " + e.getMessage());
            return false;
        }

        toDoList.addTask(event);
        ui.addMessages("Got it. I've added this task:", "  " + event);
        ui.addMessages(String.format("Now you have %d task(s) in the list.", toDoList.size()));
        return true;
    }
}
