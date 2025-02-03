package Command;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import Exception.NovaException;
import Parser.Parser;
import Task.Event;
import Task.Task;
import TaskList.TaskList;
import UI.UI;

public class EventCommand implements Command {
    private TaskList toDoList;
    private UI ui;
    private String[] msgParts;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public EventCommand(TaskList toDoList, UI ui, String instruction) throws NovaException {
        this.toDoList = toDoList;
        this.ui = ui;
        this.msgParts = instruction.split(" /from | /to ", 3);
        if (msgParts.length != 3) {
            throw new NovaException("Follow format: event <event description> /from <time> /to <time>");
        }
        try {
            this.startTime = Parser.parseDateTime(msgParts[1]);
            this.endTime = Parser.parseDateTime(msgParts[2]);
        } catch (DateTimeException e) {
            throw new NovaException("Invalid deadline time: " + e.getMessage());
        }

    }

    @Override
    public boolean execute() {
        Task event = new Event(msgParts[0].substring("event".length() + 1), startTime, endTime);
        toDoList.addTask(event);
        ui.displayMessages("Got it. I've added this task:" , "  " + event);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
