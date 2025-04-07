package seedu.address.logic.parser.exceptions;

/**
 * Signals that the index provided is empty.
 */
public class EmptyIndexException extends RuntimeException {
    public EmptyIndexException(String message) {
        super(message);
    }
}
