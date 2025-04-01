package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeleteModTutCommand;
import seedu.address.model.person.ModTutGroup;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class DeleteModTutCommandParserTest {
    private DeleteModTutCommandParser parser = new DeleteModTutCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteModTutCommand() {
        assertParseSuccess(parser, VALID_MODTUT_AMY, new DeleteModTutCommand(new ModTutGroup(VALID_MODTUT_AMY)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_MODTUT_AMY,
                "Invalid ModTutGroup format. Expected format: MODULE-TUTORIAL");

    }
}
