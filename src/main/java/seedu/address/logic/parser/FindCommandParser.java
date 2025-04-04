package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.AlwaysTrueKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.FieldContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TelegramHandleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL);

        argMultimap.verifyOnlyOnePrefixFor(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FieldContainsKeywordsPredicate predicate = new AlwaysTrueKeywordsPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {

            if (argMultimap.getValue(PREFIX_NAME).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_NAME));
            }
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));

        } else if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {

            if (argMultimap.getValue(PREFIX_TELEGRAM).get().isEmpty()) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_TELEGRAMHANDLE));
            }
            String[] telegramKeywords = argMultimap.getValue(PREFIX_TELEGRAM).get().split("\\s+");
            predicate = (new TelegramHandleContainsKeywordsPredicate(Arrays.asList(telegramKeywords)));

        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {

            if (argMultimap.getValue(PREFIX_EMAIL).get().isEmpty()) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_EMAIL));
            }
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            predicate = new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords));

        }

        assert !(predicate instanceof AlwaysTrueKeywordsPredicate) : "Predicate should be of a valid type ";

        return new FindCommand(predicate);
    }

    /**
     * Returns true if at least of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
