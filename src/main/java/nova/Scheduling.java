package nova;

import java.time.LocalDate;
import java.time.LocalDateTime;

import nova.task.Deadline;
import nova.task.Event;
import nova.task.Task;
import nova.tasklist.TaskList;

public class Scheduling {
    public static void saveTaskIfDateMatch(Task task, LocalDateTime date, TaskList taskList) {
        boolean isToday = false;
        if (task instanceof Event) {
            isToday = isEventToday(task, date);
        } else if (task instanceof Deadline) {
            isToday = isDeadlineToday(task, date);
        }

        if (isToday) {
            taskList.addTask(task);
        }
    }

    private static Boolean isEventToday(Task task, LocalDateTime date) {
        boolean isStartingLaterToday = ((Event) task).getStartTime().isAfter(date) &&
                ((Event) task).getStartTime().toLocalDate().isEqual(date.toLocalDate());
        boolean hasStarted = ((Event) task).getStartTime().isBefore(date);
        boolean hasNotEnded = ((Event) task).getEndTime().isAfter(date);
        return isStartingLaterToday || (hasStarted && hasNotEnded);
    }

    private static Boolean isDeadlineToday(Task task, LocalDateTime date) {
        return ((Deadline) task).getDueTime().toLocalDate().isEqual(date.toLocalDate());
    }
}
