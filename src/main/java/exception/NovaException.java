package exception;

/**
 * Exception thrown for errors specific to Nova.
 */
public class NovaException extends Exception {
    /**
     * Constructs a new exception specific for Nova with the specified detail message.
     *
     * @param message The detailed message to be retrieved by getMessage().
     */
    public NovaException(String message) {
        super(message);
    }
}
