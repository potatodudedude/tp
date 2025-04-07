package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP;

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

    private final String modTutGroupName;

    /**
     * Constructs a DeleteModTutCommand with the specified ModTutGroup.
     *
     * @param modTutGroupName The tutorial group to delete from persons.
     */
    public DeleteModTutCommand(String modTutGroupName) {
        this.modTutGroupName = modTutGroupName;
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
        String moduleName = modTutGroupName.split("-")[0];
        String tutorialName = modTutGroupName.split("-")[1];

        if (!ModTutGroup.getModuleMap().containsKey(moduleName)) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, modTutGroupName));
        }

        if (!ModTutGroup.getModuleMap().get(moduleName).containsKey(tutorialName)) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, modTutGroupName));
        }
        model.deleteModTut(new ModTutGroup(modTutGroupName));
        return new CommandResult(String.format(MESSAGE_DELETE_TUT_SUCCESS, modTutGroupName),
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
        return modTutGroupName.equals(otherDeleteModCommand.modTutGroupName);
    }
}
