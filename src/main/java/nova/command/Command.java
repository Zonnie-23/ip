package nova.command;

/**
 * Represents a command that can be executed.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @return true if execution was successful, false otherwise.
     */
    boolean execute();
}
