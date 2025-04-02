package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteModCommand.MESSAGE_DELETE_MOD_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Module;


public class DeleteModCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validMod_success() {
        Module module = new Module(VALID_MOD_AMY);
        DeleteModCommand deleteModCommand = new DeleteModCommand(module);

        String expectedMessage = MESSAGE_DELETE_MOD_SUCCESS + module.toString();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deleteMod(module);

        assertCommandSuccess(deleteModCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteModCommand deleteModFirstCommand = new DeleteModCommand(new Module(VALID_MOD_AMY));
        DeleteModCommand deleteModSecondCommand = new DeleteModCommand(new Module(VALID_MOD_BOB));
        //same object -> returns true
        assertTrue(deleteModFirstCommand.equals(deleteModFirstCommand));

        //same values -> returns true
        DeleteModCommand deleteModFirstCommandCopy = new DeleteModCommand(new Module(VALID_MOD_AMY));
        assertTrue(deleteModFirstCommand.equals(deleteModFirstCommandCopy));


        //different types -> returns false
        assertFalse(deleteModFirstCommand.equals(1));

        //null -> returns false
        assertFalse(deleteModFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(deleteModFirstCommand.equals(deleteModSecondCommand));
    }
}
