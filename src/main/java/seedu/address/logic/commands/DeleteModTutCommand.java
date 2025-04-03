package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModTutGroup;

/**
 * Deletes a tutorial group from all persons in the address book.
 * Also deletes persons who are taking only the specified tutorial group.
 */
public class DeleteModTutCommand extends Command {

    /** Command word to trigger this command. */
    public static final String COMMAND_WORD = "deleteTut";

    /** Usage instructions for this command. */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial as specified as well as contacts who only take that tutorial.\n"
            + "Parameters: MODULE-TUTORIAL_GROUP (Case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " CS2103T-T12";

    /** Success message displayed after the tutorial is deleted. */
    public static final String MESSAGE_DELETE_TUT_SUCCESS = "Tutorial Deleted: %1$s";

    /** The ModTutGroup to be deleted from persons and the address book. */
    private final ModTutGroup modTutGroup;

    /**
     * Constructs a DeleteModTutCommand with the specified ModTutGroup.
     *
     * @param modTutGroup The tutorial group to delete from persons.
     */
    public DeleteModTutCommand(ModTutGroup modTutGroup) {
        this.modTutGroup = modTutGroup;
    }

    /**
     * Executes the delete tutorial group command.
     * Removes the specified tutorial group from all persons.
     * Deletes any person who only has that tutorial group.
     *
     * @param model The model containing the address book and data.
     * @return A CommandResult indicating the success of the operation.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteModTut(this.modTutGroup);
        return new CommandResult(String.format(MESSAGE_DELETE_TUT_SUCCESS, modTutGroup.toString()),
                false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteModTutCommand)) {
            return false;
        }

        DeleteModTutCommand otherDeleteModCommand = (DeleteModTutCommand) other;
        return modTutGroup.equals(otherDeleteModCommand.modTutGroup);
    }
}
