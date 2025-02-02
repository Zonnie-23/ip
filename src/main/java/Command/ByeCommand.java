package Command;

import Storage.Storage;
import TaskList.TaskList;
import UI.UI;

import java.util.Scanner;

public class ByeCommand implements Command {
    private final TaskList toDoList;
    private final Storage storage;
    private final Scanner scanner;
    private final UI ui;

    public ByeCommand(TaskList toDoList, Storage storage, Scanner scanner, UI ui) {
        this.toDoList = toDoList;
        this.storage = storage;
        this.scanner = scanner;
        this.ui = ui;
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
