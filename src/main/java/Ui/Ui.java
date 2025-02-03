package Ui;

import java.util.List;

import Task.Task;
import TaskList.TaskList;

public class Ui {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________";

    public Ui() {}

    /**
     * Displays the top horizontal seperator to frame the UI.
     */
    public void open() {
        displaySeperator();
    }

    /**
     * Displays the bottom horizontal seperator to frame the UI.
     */
    public void close() {
        displaySeperator();
    }

    /**
     * Prints an array of messages to the console, each prefixed with an indent and to be on a new line.
     * @param messages The message(s) to be displayed.
     */
    public void displayMessages(String... messages) {
        for (String message : messages) {
            System.out.println("    " + message);
        }
    }

    /**
     * Displays the list of tasks with user-friendly numbering.
     * @param taskList The list of tasks to be displayed.
     */
    public void displayTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints a horizontal seperator line.
     */
    private void displaySeperator() {
        System.out.println(HORIZONTAL_BAR);
    }
}
