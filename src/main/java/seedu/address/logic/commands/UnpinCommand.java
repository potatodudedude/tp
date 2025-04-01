package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Unpins a person from the list.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unpins a person from the list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Unpinned Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_UNPINNED = "This person is already unpinned.";

    private final Index index; // One-based index

    public UnpinCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnpin = lastShownList.get(index.getZeroBased());
        if (!personToUnpin.getPin()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_UNPINNED);
        }
        Person unpinnedPerson = createUnpinnedPerson(personToUnpin);

        assert !unpinnedPerson.getPin() : "Newly created pinnedPerson's pin status should be false";
        model.unpinPerson(personToUnpin, unpinnedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(unpinnedPerson)));
    }

    private Person createUnpinnedPerson(Person personToUnpin) {
        Name name = personToUnpin.getName();
        TelegramHandle telegramHandle = personToUnpin.getTelegramHandle();
        Email email = personToUnpin.getEmail();
        Set<ModTutGroup> modTutGroups = personToUnpin.getModTutGroups();
        Set<Tag> tags = personToUnpin.getTags();
        return new Person(name, telegramHandle, email, modTutGroups, tags, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinCommand)) {
            return false;
        }

        UnpinCommand otherUnpinCommand = (UnpinCommand) other;
        return index.equals(otherUnpinCommand.index);
    }
}
