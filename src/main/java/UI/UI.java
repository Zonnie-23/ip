package UI;

import Task.Task;
import TaskList.TaskList;

import java.util.List;


public class UI {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________";

    public UI() {}

    public void displayMessages(String... messages) {
        for (String message : messages) {
            System.out.println("    " + message);
        }
    }

    private void displaySeperator() {
        System.out.println(HORIZONTAL_BAR);
    }

    public void displayTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        System.out.println("    Here are the tasks in your list: ");
        for (int i = 1; i <= taskList.size(); i++) {
            System.out.println("    " + i + "." + tasks.get(i - 1));
        }
    }

    public void open() {
        displaySeperator();
    }

    public void close() {
        displaySeperator();
    }
}
