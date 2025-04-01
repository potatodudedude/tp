package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteModCommand;
import seedu.address.model.person.Module;


public class DeleteModCommandParserTest {

    private DeleteModCommandParser parser = new DeleteModCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteModCommand() {
        assertParseSuccess(parser, VALID_MOD_AMY, new DeleteModCommand(new Module(VALID_MOD_AMY)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModCommand.MESSAGE_USAGE));
    }
}
