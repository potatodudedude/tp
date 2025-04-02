package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> filteredList;
        if (model.isViewAll()) {
            filteredList = model.getFilteredPersonList();
        } else {
            List<String> selectedTabs = model.getSelectedTabs();
            String moduleName = selectedTabs.get(0);
            String tutorialName = selectedTabs.get(1);

            List<Person> personList = model.getAddressBook().getPersonList();
            filteredList = personList.stream()
                    .filter(p -> {
                        Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                        Stream<Module> moduleStream = modTutGroups.stream().map(ModTutGroup::getModule);
                        return moduleStream.anyMatch(m -> m.getName().equals(moduleName));
                    })
                    .filter(p -> {
                        Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                        Stream<Tutorial> tutorialStream = modTutGroups.stream().map(ModTutGroup::getTutorial);
                        return tutorialStream.anyMatch(m -> m.getName().equals(tutorialName));
                    }).toList();
        }

        if (targetIndex.getZeroBased() >= filteredList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = filteredList.get(targetIndex.getZeroBased());

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
