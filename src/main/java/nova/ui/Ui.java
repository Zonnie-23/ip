package nova.ui;

import java.util.List;

import nova.task.Task;
import nova.tasklist.TaskList;

public class Ui {
    private StringBuilder response = new StringBuilder();

    /**
     * Prints an array of messages to the console, each prefixed with an indent and to be on a new line.
     * @param messages The message(s) to be displayed.
     */
    public void addMessages(String... messages) {
        for (String msg : messages) {
            response.append(msg).append("\n");
        }
    }

    /**
     * Displays the list of tasks with user-friendly numbering.
     * @param taskList The list of tasks to be displayed.
     */
    public void displayTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        for (int i = 0; i < taskList.size(); i++) {
            response.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
        }
    }

    public String generateResponse() {
        return response.toString();
    }

    public void reinitialiseResponse() {
        response = new StringBuilder();
    }
}
