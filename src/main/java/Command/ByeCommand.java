package Command;

import java.util.Scanner;

import Storage.Storage;
import TaskList.TaskList;
import UI.UI;

public class ByeCommand implements Command {
    private final TaskList toDoList;
    private final UI ui;
    private final Storage storage;
    private final Scanner scanner;

    public ByeCommand(TaskList toDoList, UI ui, Storage storage, Scanner scanner) {
        this.toDoList = toDoList;
        this.ui = ui;
        this.storage = storage;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        ui.displayMessages("Do you want to save your todo list? Type \"yes\" to save");
        if (scanner.nextLine().startsWith("y")) {
            boolean status = storage.saveTask(toDoList);
            if (status) {
                ui.displayMessages("Your file has been saved. Hope to see you again soon!");
            } else {
                ui.displayMessages("File not saved!");
                return false;
            }
        } else {
            ui.displayMessages("Bye. Hope to see you again soon!");
        }
        return true;
    }
}
