package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_viewAllFromViewTabs_success() {
        model.setViewAll(false);
        CommandResult expectedCommandResult = new CommandResult(ViewCommand.MESSAGE_SUCCESS_VIEW_ALL,
                true, false, false);

        ViewCommand viewCommand = new ViewCommand();
        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
        assertTrue(model.isViewAll());
    }
}
