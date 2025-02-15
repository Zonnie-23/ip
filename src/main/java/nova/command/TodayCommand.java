package nova.command;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import nova.exception.NovaException;
import nova.task.Deadline;
import nova.task.Event;
import nova.task.Task;
import nova.task.Todo;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class TodayCommand implements Command {
    private Command scheduleCommand;

    public TodayCommand(Ui ui, TaskList taskList) throws NovaException {
        try {
            scheduleCommand = new ScheduleCommand(ui, taskList, LocalDateTime.now());
        } catch (NovaException e) {
            throw new NovaException("Error creating schedule for today.");
        }
    }

    public boolean execute() {
        scheduleCommand.execute();
        return true;
    }
}
