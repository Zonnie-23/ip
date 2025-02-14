package nova.command;

import java.util.List;

import nova.ui.Ui;


public class HelpCommand implements Command {
    private final Ui ui;
    private final List<String> commands;

    public HelpCommand(Ui ui, List<String> commands) {
        this.ui = ui;
        this.commands = commands;
    }

    @Override
    public boolean execute() {
        ui.addMessages("I accept the following instructions:", commands.toString());
        return true;
    }
}
