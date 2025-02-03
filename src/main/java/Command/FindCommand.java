package Command;

import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

/**
 * Represents a command that searches for tasks containing a specific keyword.
 * <p>
 * When executed, this command scans through the provided task list and collects all tasks
 * whose descriptions contain the given keyword. It then displays the matching tasks using the UI.
 * </p>
 */
public class FindCommand implements Command {
    private final TaskList toDoList;
    private final String keyword;
    private final Ui ui;
    private final TaskList matchedTaskList = new TaskList();

    /**
     * Constructs a new FindCommand with the specified task list, UI, and search instruction.
     * <p>
     * The search keyword is extracted from the instruction by removing the "find" command prefix.
     * For example, if the instruction is "find homework", the keyword will be "homework".
     * </p>
     *
     * @param toDoList    the task list in which to search for matching tasks.
     * @param ui          the UI component used for displaying messages and tasks.
     * @param instruction the full search instruction that contains the keyword after the "find" prefix.
     */
    public FindCommand(TaskList toDoList, Ui ui, String instruction) {
        this.toDoList = toDoList;
        this.ui = ui;
        this.keyword = instruction.substring("find".length() + 1);
    }

    /**
     * Executes the find command.
     * <p>
     * This method iterates through all tasks in the given task list. For each task that contains
     * the specified keyword, the task is added to a new list of matching tasks. Once all tasks have been checked,
     * the UI displays the matching tasks.
     * </p>
     *
     * @return {@code true} if the command executed successfully.
     */
    @Override
    public boolean execute() {
        for (int i = 0; i < toDoList.size(); i++) {
            Task currTask = toDoList.getTask(i);
            if (currTask.contains(keyword)) {
                matchedTaskList.addTask(currTask);
            }
        }
        ui.displayMessages("Here are the matching tasks in your list: ");
        ui.displayTasks(matchedTaskList);
        return true;
    }
}
