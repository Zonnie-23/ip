package Command;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import Exception.NovaException;
import Parser.Parser;
import Task.Event;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

/**
 * Command to add an event task to the task list.
 */
public class EventCommand implements Command {
    private TaskList toDoList;
    private Ui ui;
    private String[] instruction;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Constructs a new deadline command and verifies that the inputs are valid.
     *
     * @param toDoList    the task list to which the deadline will be added.
     * @param ui          the user interface for displaying messages.
     * @param instruction the command instruction containing the task description and deadline.
     * @throws NovaException if the instruction format is invalid or the deadline time cannot be parsed.
     */
    public EventCommand(TaskList toDoList, Ui ui, String instruction) throws NovaException {
        this.toDoList = toDoList;
        this.ui = ui;
        this.instruction = instruction.split(" /from | /to ", 3);
        if (this.instruction.length != 3) {
            throw new NovaException("Follow format: event <event description> /from <time> /to <time>");
        }
        try {
            this.startTime = Parser.parseDateTime(this.instruction[1]);
            this.endTime = Parser.parseDateTime(this.instruction[2]);
        } catch (DateTimeException e) {
            throw new NovaException("Invalid deadline time: " + e.getMessage());
        }

    }

    /**
     * Executes the event command by creating a new event task and adding it to the task list.
     *
     * @return true if the task was added successfully.
     */
    @Override
    public boolean execute() {
        Task event = new Event(instruction[0].substring("event".length() + 1), startTime, endTime);
        toDoList.addTask(event);
        ui.displayMessages("Got it. I've added this task:" , "  " + event);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
