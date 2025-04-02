package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getPinnedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class PinCommandTest {

    // model list contains [ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE]
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_pin_success() {
        model.setViewAll(true);
        Person pinnedPerson = TypicalPersons.PINNED_CARL;
        PinCommand pinCommand = new PinCommand(INDEX_THIRD_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, Messages.format(pinnedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.pinPerson(expectedModel.getFilteredPersonList().get(2), pinnedPerson);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        model.setViewAll(true);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);
        assertCommandFailure(pinCommand, model , Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyPinnedPerson_failure() {
        Model actualModel = new ModelManager(getPinnedTypicalAddressBook(), new UserPrefs());
        actualModel.setViewAll(true);
        PinCommand pinCommand1 = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinCommand2 = new PinCommand(INDEX_SECOND_PERSON);
        assertCommandFailure(pinCommand1, actualModel, PinCommand.MESSAGE_PERSON_ALREADY_PINNED);
        assertCommandFailure(pinCommand2, actualModel, PinCommand.MESSAGE_PERSON_ALREADY_PINNED);
    }


    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assert (pinFirstCommand.equals(pinFirstCommand));

        // same values -> returns true
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_PERSON);
        assert (pinFirstCommand.equals(pinFirstCommandCopy));

        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
    }
}
