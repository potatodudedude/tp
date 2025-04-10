package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to ConnectS.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to ConnectS.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TELEGRAM + "TELEGRAM_HANDLE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MOD + "MODULE-TUTORIAL_GROUP... "
            + "[" + PREFIX_TAG + "TAG...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TELEGRAM + "@johndoe123 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_MOD + "CS2103T-T21 "
            + PREFIX_MOD + "CS2109S-T05 "
            + PREFIX_TAG + "struggling "
            + PREFIX_TAG + "exchangestudent";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A person with the same name, email or telegram handle "
            + "already exists in ConnectS";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!model.isViewAll()) {
            String moduleName = toAdd.getModTutGroups().iterator().next().getModule().getName();
            String tutorialName = toAdd.getModTutGroups().iterator().next().getTutorial().getName();
            model.setSelectedTabs(moduleName, tutorialName);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
