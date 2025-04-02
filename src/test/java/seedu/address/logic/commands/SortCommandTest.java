package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sort_success() {
        Model model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs());
        expectedModel.sortAddressBook(SortCommand.COMPARATOR);
        assertCommandSuccess(new SortCommand(), model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
