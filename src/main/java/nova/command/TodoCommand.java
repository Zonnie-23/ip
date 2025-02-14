package nova.command;

import nova.exception.NovaException;
import nova.task.Task;
import nova.task.Todo;
import nova.tasklist.TaskList;
import nova.ui.Ui;

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
        assert instruction != null;
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
        ui.addMessages("Got it. I've added this task:" , "  " + todo);
        ui.addMessages(String.format("Now you have %d tasks in the list.", toDoList.size()));
        return true;
    }
}
