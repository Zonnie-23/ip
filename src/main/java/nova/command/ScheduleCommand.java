package nova.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import nova.Scheduling;
import nova.exception.NovaException;
import nova.parser.Parser;
import nova.task.Task;
import nova.tasklist.TaskList;
import nova.ui.Ui;

public class ScheduleCommand implements Command {
    private Ui ui;
    private TaskList taskList;
    private String[] instruction;
    private LocalDateTime targetDateTime;
    private TaskList tasksToday = new TaskList();

    public ScheduleCommand(Ui ui, TaskList taskList, String instruction) throws NovaException {
        this.ui = ui;
        this.taskList = taskList;
        this.instruction = instruction.split(" /on ", 2);
        if (this.instruction.length != 2) {
            throw new NovaException("Follow format: schedule /on <date>");
        }
        try {
            LocalTime time = LocalTime.of(0, 0);
            LocalDate date = Parser.parseDate(this.instruction[1]);
            targetDateTime = LocalDateTime.of(date, time);
        } catch (NovaException e) {
            throw new NovaException("Invalid date format: " + e.getMessage());
        }
    }

    public ScheduleCommand(Ui ui, TaskList taskList, LocalDateTime date) throws NovaException {
        this.ui = ui;
        this.taskList = taskList;
        this.targetDateTime = date;
    }

    public boolean execute() {
        for (Task task : taskList.getTasks()) {
            Scheduling.saveTaskIfDateMatch(task, targetDateTime, tasksToday);
        }

        if (tasksToday.size() == 0) {
            ui.addMessages(String.format("There are no tasks for %s.",
                    Parser.outputDate(targetDateTime.toLocalDate())));
            return true;
        } else {
            ui.addMessages(String.format("Here are your tasks for %s:",
                    Parser.outputDate(targetDateTime.toLocalDate())));
            tasksToday.sort();
            ui.displayTasks(tasksToday);
            return true;
        }
    }

}
