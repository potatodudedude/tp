package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteModTutCommand.MESSAGE_DELETE_TUT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModTutGroup;


public class DeleteModTutCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validModTut_success() {
        ModTutGroup modTutGroup = new ModTutGroup(VALID_MODTUT_AMY);
        DeleteModTutCommand deleteModTutCommand = new DeleteModTutCommand(modTutGroup);

        String expectedMessage = String.format(MESSAGE_DELETE_TUT_SUCCESS, modTutGroup.toString());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deleteModTut(modTutGroup);

        assertCommandSuccess(deleteModTutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteModTutCommand deleteModTutFirstCommand = new DeleteModTutCommand(new ModTutGroup(VALID_MODTUT_AMY));
        DeleteModTutCommand deleteModTutSecondCommand = new DeleteModTutCommand(new ModTutGroup(VALID_MODTUT_BOB));
        //same object -> returns true
        assertTrue(deleteModTutFirstCommand.equals(deleteModTutFirstCommand));

        //same values -> returns true
        DeleteModTutCommand deleteModTutFirstCommandCopy = new DeleteModTutCommand(new ModTutGroup(VALID_MODTUT_AMY));
        assertTrue(deleteModTutFirstCommand.equals(deleteModTutFirstCommandCopy));


        //different types -> returns false
        assertFalse(deleteModTutFirstCommand.equals(1));

        //null -> returns false
        assertFalse(deleteModTutFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(deleteModTutFirstCommand.equals(deleteModTutSecondCommand));
    }
}
