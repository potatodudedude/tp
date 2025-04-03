package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModTutGroup;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MOD);

        if (!arePrefixesPresent(argMultimap, PREFIX_MOD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyOnlyOnePrefixFor(PREFIX_MOD);

        if (argMultimap.getValue(PREFIX_MOD).isEmpty() || argMultimap.getValue(PREFIX_MOD).get().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        String modTutGroup = argMultimap.getValue(PREFIX_MOD).get();

        // If getting view for all
        if (modTutGroup.equalsIgnoreCase("ALL")) {
            return new ViewCommand();
        }

        // If getting view for specific module
        if (!ModTutGroup.isValidModTutGroup(modTutGroup)) {
            throw new ParseException(String.format(MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, modTutGroup));
        }
        String moduleName = modTutGroup.split("-")[0];
        String tutorialName = modTutGroup.split("-")[1];

        return new ViewCommand(modTutGroup, moduleName, tutorialName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
