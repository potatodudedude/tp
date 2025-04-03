package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_SUCCESS_VIEW_TAB;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Person;

public class ViewCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_viewAllFromViewTabs_success() {
        model.setViewAll(false);
        CommandResult expectedCommandResult = new CommandResult(ViewCommand.MESSAGE_SUCCESS_VIEW_ALL, true, false);

        ViewCommand viewCommand = new ViewCommand();
        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
        assertTrue(model.isViewAll());
    }

    @Test
    public void execute_viewTabsFromViewAll_success() {
        model.setViewAll(true);
        Person person = model.getFilteredPersonList().iterator().next();
        ModTutGroup modTutGroup = person.getModTutGroups().iterator().next();
        String moduleName = modTutGroup.getModule().getName();
        String tutorialName = modTutGroup.getTutorial().getName();
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS_VIEW_TAB, modTutGroup),
                true, false);

        ViewCommand viewCommand = new ViewCommand(modTutGroup.toString(), moduleName, tutorialName);
        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
        assertFalse(model.isViewAll());
    }
}
