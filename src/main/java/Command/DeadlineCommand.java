package Command;

import Exception.NovaException;
import Parser.Parser;
import Task.Deadline;
import Task.Task;
import TaskList.TaskList;
import UI.UI;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class DeadlineCommand implements Command {
    private LocalDateTime deadlineTime;
    private String[] msgParts;
    private TaskList toDoList;
    private UI ui;

    public DeadlineCommand(String instruction, TaskList toDoList, UI ui) throws NovaException {
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

    @Override
    public boolean execute() {
        Task event = new Deadline(msgParts[0].substring("deadline".length() + 1), deadlineTime);
        toDoList.addTask(event);
        ui.displayMessages("Got it. I've added this task:" , "  " + event);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
