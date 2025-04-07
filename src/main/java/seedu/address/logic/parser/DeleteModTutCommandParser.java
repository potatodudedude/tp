package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP;

import seedu.address.logic.commands.DeleteModTutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModTutGroup;

/**
 * Parses the given {@code String} of arguments in the context of the DeleteModTutCommand
 * and returns a DeleteModTutCommand object for execution.
 * @throws ParseException if the user input does not conform to the expected format
 */
public class DeleteModTutCommandParser implements Parser<DeleteModTutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModTutCommand
     * and returns a DeleteModTutCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteModTutCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String keyword = args.trim();
        if (keyword.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModTutCommand.MESSAGE_USAGE));
        }

        if (!ModTutGroup.isValidModTutGroup(keyword)) {
            throw new ParseException(String.format(MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, keyword));
        }

        try {
            return new DeleteModTutCommand(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid ModTutGroup format. Expected format: MODULE-TUTORIAL", e);
        }
    }
}
