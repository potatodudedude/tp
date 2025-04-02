package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TelegramHandleContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validSpaceArgs_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgFields_success() {
        // name keyword
        assertParseSuccess(parser, " n/alice bob",
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"))));

        // telegram handle keyword
        assertParseSuccess(parser, " t/@alice builder",
                new FindCommand(new TelegramHandleContainsKeywordsPredicate(Arrays.asList("@alice", "builder"))));

        // email keyword
        assertParseSuccess(parser, " e/alice@gmail bob2001@ymail.com",
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@gmail", "bob2001@ymail.com"))));
    }

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSamePrefixes_failure() {
        // multiple Names
        assertParseFailure(parser, " n/amy n/Amy", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple Telegram Handles
        assertParseFailure(parser, " t/@amy t/_yeoh", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // multiple Emails
        assertParseFailure(parser, " e/@gmail e/alexy", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
    }

    @Test
    public void parse_multipleDifferentPrefixes_failure() {
        assertParseFailure(parser, " n/amy t/@amy e/@am@gmail.com", Messages.MESSAGE_MORE_THAN_ONE_FIELD);
    }

    @Test
    public void parse_emptyKeyword_failure() {
        // empty name
        assertParseFailure(parser, " n/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_NAME));

        // empty telegram handle
        assertParseFailure(parser, " t/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_TELEGRAMHANDLE));

        // empty email
        assertParseFailure(parser, " e/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_EMPTY_EMAIL));
    }
}
