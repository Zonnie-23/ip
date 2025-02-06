package command;

import exception.NovaException;
import task.Task;
import task.Todo;
import tasklist.TaskList;
import ui.Ui;

/**
 * Command to add a todo task to the task list.
 */
public class TodoCommand implements Command {
    private TaskList toDoList;
    private Ui ui;
    private String description;

    /**
     * Constructs a new TodoCommand and verifies validity of inputs.
     *
     * @param toDoList    the task list to which the todo will be added.
     * @param ui          the user interface for displaying messages.
     * @param instruction the command instruction containing the task description.
     * @throws NovaException if the description is empty.
     */
    public TodoCommand(TaskList toDoList, Ui ui, String instruction) throws NovaException {
        this.toDoList = toDoList;
        this.ui = ui;
        this.description = instruction.substring("todo".length() + 1);
        if (this.description.isEmpty()) {
            throw new NovaException("Follow format: todo <todo description>");
        }
    }

    /**
     * Executes the todo command by creating a new todo task and adding it to the task list.
     *
     * @return true if the task was added successfully.
     */
    @Override
    public boolean execute() {
        Task todo = new Todo(this.description);
        toDoList.addTask(todo);
        ui.displayMessages("Got it. I've added this task:" , "  " + todo);
        ui.displayMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
