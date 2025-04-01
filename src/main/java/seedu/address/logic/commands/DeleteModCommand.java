package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Module;
public class DeleteModCommand extends Command {
    public static final String COMMAND_WORD = "deleteMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module as specified as well as contacts who only takes the course.\n"
            + "Parameters: Module Code\n"
            + "Example: " + COMMAND_WORD + " CS2103T";
    private final Module module;

    public static final String MESSAGE_DELETE_MOD_SUCCESS = "Course Deleted";

    public DeleteModCommand(Module module) {
        this.module = module;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteMod(this.module);
        return new CommandResult(MESSAGE_DELETE_MOD_SUCCESS + module.toString(), false, false);
    }
}
