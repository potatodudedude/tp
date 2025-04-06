package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteModCommand.MESSAGE_DELETE_MOD_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;



public class DeleteModCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    public void setUp() throws Exception {
        // Set up a clean AddressBook and reset moduleMap before each test
        model = new ModelManager(new AddressBook(), new UserPrefs());
        resetModuleMap();
    }

    private void resetModuleMap() throws Exception {
        Field mapField = ModTutGroup.class.getDeclaredField("moduleMap");
        mapField.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Module> moduleMap = (Map<String, Module>) mapField.get(null);
        moduleMap.clear();

        // Re-register modules deleted
        new ModTutGroup(VALID_MODTUT_AMY);
    }




    @Test
    public void execute_validMod_success() throws Exception {
        Module moduleToDelete = new Module("CS1234");
        DeleteModCommand deleteModCommand = new DeleteModCommand(moduleToDelete);

        String expectedMessage = String.format(MESSAGE_DELETE_MOD_SUCCESS, moduleToDelete.toString());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deleteMod(moduleToDelete);
        resetModuleMap();

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
