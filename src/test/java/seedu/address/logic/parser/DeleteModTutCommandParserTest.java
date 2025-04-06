package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteModTutCommand;

public class DeleteModTutCommandParserTest {
    private DeleteModTutCommandParser parser = new DeleteModTutCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteModTutCommand() {
        assertParseSuccess(parser, VALID_MODTUT_AMY, new DeleteModTutCommand(VALID_MODTUT_AMY));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModTutCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_MODTUT_AMY,
                String.format(MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, INVALID_MODTUT_AMY));
    }
}
