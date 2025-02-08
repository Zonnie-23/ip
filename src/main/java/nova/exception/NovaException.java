package nova.exception;

/**
 * Exception thrown for errors specific to Nova chatbot.
 */
public class NovaException extends Exception {
    /**
     * Constructs a new exception specific for Nova chatbot with the specified detail message.
     *
     * @param message The detailed message to be retrieved by getMessage().
     */
    public NovaException(String message) {
        super(message);
    }
}
