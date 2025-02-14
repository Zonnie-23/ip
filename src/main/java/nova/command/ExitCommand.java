package nova.command;

import nova.ui.Ui;

public class ExitCommand implements Command {
    private Ui ui;

    public ExitCommand(Ui ui) {
        this.ui = ui;
    }

    public boolean execute() {
        ui.addMessages("Bye. Hope to see you again soon!");
        return true;
    }
}
