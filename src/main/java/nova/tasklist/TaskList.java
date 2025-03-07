package nova.tasklist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nova.task.Task;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks List of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * ADds a task to the list.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index Index of the task.
     * @return Task at the given index.
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes and return task at the specified index from the list
     *
     * @param index Index of the task to be removed.
     * @return Removed task.
     */
    public Task removeTask(int index) {
        return this.tasks.remove(index);
    }

    public int size() {
        return this.tasks.size();
    }

    public void sort() {
        tasks.sort(Comparator.comparing(Task::getDateTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
