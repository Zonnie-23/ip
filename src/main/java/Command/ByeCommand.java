package Command;

import Storage.Storage;
import Task.Task;
import UI.UI;

import java.util.List;
import java.util.Scanner;

public class ByeCommand implements Command {
    private final List<Task> toDoList;
    private final Storage storage;
    private final Scanner scanner;

    public ByeCommand(List<Task> toDoList, Storage storage, Scanner scanner) {
        this.toDoList = toDoList;
        this.storage = storage;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        UI.displayCompleteMessage("Do you want to save your todo list? Type \"yes\" to save");
        if (scanner.nextLine().startsWith("y")) {
            boolean status = storage.saveTask(toDoList);
            if (status) {
                UI.displayCompleteMessage("Your file has been saved. Hope to see you again soon!");
            } else {
                return true;
            }
        } else {
            UI.displayCompleteMessage("Bye. Hope to see you again soon!");
        }
        return false;
    }
}
