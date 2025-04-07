package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.exceptions.EmptyIndexException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnpinCommand object
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand
     * and returns a UnpinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnpinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnpinCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (EmptyIndexException e) {
            throw new ParseException(MESSAGE_EMPTY_INDEX);
        }
    }
}
