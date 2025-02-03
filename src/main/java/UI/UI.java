package UI;

import Task.Task;
import TaskList.TaskList;

import java.util.List;


public class UI {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________";

    public UI() {}

    /**
     * Displays the top horizontal seperator to frame the UI
     */
    public void open() {
        displaySeperator();
    }

    /**
     * Displays the bottom horizontal seperator to frame the UI
     */
    public void close() {
        displaySeperator();
    }

    /**
     * Prints an array of messages to the console, each prefixed with an indent and to be on a new line
     * @param messages
     */
    public void displayMessages(String... messages) {
        for (String message : messages) {
            System.out.println("    " + message);
        }
    }

    /**
     * Displays the list of tasks with user-friendly numbering
     * @param taskList
     */
    public void displayTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        System.out.println("    Here are the tasks in your list: ");
        for (int i = 0; i <= taskList.size(); i++) {
            System.out.println("    " + (i - 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints a horizontal seperator line
     */
    private void displaySeperator() {
        System.out.println(HORIZONTAL_BAR);
    }
}
