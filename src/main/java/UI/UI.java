package UI;

import Task.Task;

import java.util.List;


public class UI {
    private static final String HORIZONTAL_BAR = "    ____________________________________________________________\n";

    public static void displayCompleteMessage(String... messages) {
        displaySeperator();
        for (String message : messages) {
            System.out.println("    " + message);
        }
        displaySeperator();
    }

    private static void displaySeperator() {
        System.out.println(HORIZONTAL_BAR);
    }

    public static void displayTaskList(List<Task> taskList) {
        displaySeperator();
        System.out.println("    Here are the tasks in your list: ");
        for (int i = 1; i <= taskList.size(); i++) {
            System.out.println("    " + i + "." + taskList.get(i - 1));
        }
        displaySeperator();
    }
}
