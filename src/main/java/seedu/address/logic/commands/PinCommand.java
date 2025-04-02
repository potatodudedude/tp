package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;

/**
 * Pins a person to the top of the list.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins a person to the top of the list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_PINNED = "This person is already pinned.";

    private final Index index; // One-based index

    public PinCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> filteredList;
        if (model.isViewAll()) {
            filteredList = model.getFilteredPersonList();
        } else {
            filteredList = model.getCurrentTabPersonList();
        }

        if (index.getZeroBased() >= filteredList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToPin = filteredList.get(index.getZeroBased());
        if (personToPin.getPin()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_PINNED);
        }
        Person pinnedPerson = createPinnedPerson(personToPin);

        assert pinnedPerson.getPin() : "Newly created pinnedPerson's pin status should be true";
        model.pinPerson(personToPin, pinnedPerson);
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, Messages.format(pinnedPerson)));
    }

    private Person createPinnedPerson(Person personToPin) {
        Name name = personToPin.getName();
        TelegramHandle telegramHandle = personToPin.getTelegramHandle();
        Email email = personToPin.getEmail();
        Set<ModTutGroup> modTutGroups = personToPin.getModTutGroups();
        Set<Tag> tags = personToPin.getTags();
        return new Person(name, telegramHandle, email, modTutGroups, tags, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand otherPinCommand = (PinCommand) other;
        return index.equals(otherPinCommand.index);
    }
}
