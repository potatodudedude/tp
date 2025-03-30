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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToPin = lastShownList.get(index.getZeroBased());
        if (personToPin.getPin()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_PINNED);
        }
        Person pinnedPerson = createPinnedPerson(personToPin);

        assert pinnedPerson.getPin() : "Person's pin status should be true before pinning";
        model.pinPerson(personToPin, pinnedPerson);
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, Messages.format(pinnedPerson)));
    }

    private Person createPinnedPerson(Person personToPin) {
        Name name = personToPin.getName();
        TelegramHandle telegramHandle = personToPin.getTelegramHandle();
        Email email = personToPin.getEmail();
        Set<ModTutGroup> modTutGroups = personToPin.getModTutGroups();
        return new Person(name, telegramHandle, email, modTutGroups, true);
    }
}
