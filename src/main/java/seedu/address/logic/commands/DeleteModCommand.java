package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Module;

/**
 * Deletes a module from all persons in the address book.
 * Also deletes persons who are taking only the specified module.
 */
public class DeleteModCommand extends Command {

    /** Command word to trigger this command. */
    public static final String COMMAND_WORD = "deleteMod";

    /** Usage instructions for this command. */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module as specified, as well as contacts who only take that module.\n"
            + "Parameters: MODULE_CODE (Case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    /** Success message displayed after module is deleted. */
    public static final String MESSAGE_DELETE_MOD_SUCCESS = "Course Deleted: %1$s";

    /** The module to be deleted from the address book. */
    private final Module module;

    /**
     * Constructs a DeleteModCommand with the specified module.
     *
     * @param module The module to delete from persons and the address book.
     */
    public DeleteModCommand(Module module) {
        this.module = module;
    }

    /**
     * Executes the delete module command.
     * Removes the specified module from all persons. Deletes any person who only has that module.
     *
     * @param model The model containing the address book and data.
     * @return A CommandResult indicating the success of the operation.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteMod(this.module);
        return new CommandResult(String.format(MESSAGE_DELETE_MOD_SUCCESS, module.toString()), false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteModCommand)) {
            return false;
        }

        DeleteModCommand otherDeleteModCommand = (DeleteModCommand) other;
        return module.equals(otherDeleteModCommand.module);
    }
}
