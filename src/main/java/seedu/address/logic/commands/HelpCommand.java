package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Parameters: None";

    public static final String SHOWING_HELP_MESSAGE = HelpCommand.showHelpMessage();

    private static String showHelpMessage() {
        return AddCommand.MESSAGE_USAGE + "\n\n" + ClearCommand.MESSAGE_USAGE + "\n\n" + DeleteCommand.MESSAGE_USAGE
                + "\n\n" + EditCommand.MESSAGE_USAGE + "\n\n" + ExitCommand.MESSAGE_USAGE + "\n\n"
                + FindCommand.MESSAGE_USAGE + "\n\n" + SortCommand.MESSAGE_USAGE + "\n\n"
                + HelpCommand.MESSAGE_USAGE + "\n\n" + ListCommand.MESSAGE_USAGE;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }
}
