package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getPinnedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class UnpinCommandTest {
    // model list contains [PINNED_ALICE, PINNED_CARL, BENSON, DANIEL, ELLE, FIONA, GEORGE]
    private Model model = new ModelManager(getPinnedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unpin_success() {
        Person unpinnedPerson = TypicalPersons.CARL;
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_SUCCESS, Messages.format(unpinnedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.unpinPerson(expectedModel.getFilteredPersonList().get(1), unpinnedPerson);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);
        assertCommandFailure(unpinCommand, model , Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyUnpinnedPerson_failure() {
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_THIRD_PERSON);
        assertCommandFailure(unpinCommand, model, UnpinCommand.MESSAGE_PERSON_ALREADY_UNPINNED);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        //same object -> returns true
        assert(unpinFirstCommand.equals(unpinFirstCommand));

        //same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assert(unpinFirstCommand.equals(unpinFirstCommandCopy));

        //different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        //null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }
}
