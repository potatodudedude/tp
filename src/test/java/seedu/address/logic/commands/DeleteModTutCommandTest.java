package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_BOB;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class DeleteModTutCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidModTut_throwsCommandException() {
        String nonExistentModTutGroup = "Invalid-TutGroup";

        DeleteModTutCommand deleteModTutCommand = new DeleteModTutCommand(nonExistentModTutGroup);

        assertThrows(CommandException.class, () -> deleteModTutCommand.execute(model));
    }

    @Test
    public void equals() {
        DeleteModTutCommand deleteModTutFirstCommand = new DeleteModTutCommand(VALID_MODTUT_AMY);
        DeleteModTutCommand deleteModTutSecondCommand = new DeleteModTutCommand(VALID_MODTUT_BOB);
        //same object -> returns true
        assertTrue(deleteModTutFirstCommand.equals(deleteModTutFirstCommand));

        //same values -> returns true
        DeleteModTutCommand deleteModTutFirstCommandCopy = new DeleteModTutCommand(VALID_MODTUT_AMY);
        assertTrue(deleteModTutFirstCommand.equals(deleteModTutFirstCommandCopy));


        //different types -> returns false
        assertFalse(deleteModTutFirstCommand.equals(1));

        //null -> returns false
        assertFalse(deleteModTutFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(deleteModTutFirstCommand.equals(deleteModTutSecondCommand));
    }
}
