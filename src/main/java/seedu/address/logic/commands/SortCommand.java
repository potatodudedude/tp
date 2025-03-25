package seedu.address.logic.commands;

import static java.util.Objects.compare;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the list of persons in the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of persons in the address book in alphabetical order.\n"
            + "Parameters: None";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    // Comparator to compare the names of two persons in lexicographical order
    public static final Comparator<Person> COMPARATOR = (person1, person2)
            -> person1.getName().fullName.compareTo(person2.getName().fullName);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAddressBook(COMPARATOR);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
