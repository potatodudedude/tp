package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModTutGroup;


public class DeleteModTutCommand extends Command {
    public static final String COMMAND_WORD = "deleteTut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial as specified as well as contacts who only takes the tutorial.\n"
            + "Parameters: Module-Tutorial\n"
            + "Example: " + COMMAND_WORD + " CS2103T-T12";
    private final ModTutGroup modTutGroup;

    public static final String MESSAGE_DELETE_MOD_SUCCESS = "Tutorial Deleted ";

    public DeleteModTutCommand(ModTutGroup modTutGroup) {
        this.modTutGroup = modTutGroup;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteModTut(this.modTutGroup);
        return new CommandResult(MESSAGE_DELETE_MOD_SUCCESS + modTutGroup.toString(), false, false);
    }


}