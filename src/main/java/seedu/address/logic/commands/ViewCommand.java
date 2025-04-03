package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;

import java.util.Map;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModTutGroup;

/**
 * Changes the tab view of the GUI to the specified Module-Tutorial Group.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View a specific tab.\n" + "Parameters: "
            + PREFIX_MOD + "MOD-TUT_GROUP" + " or " + PREFIX_MOD + "all (Case-insensitive)";

    public static final String MESSAGE_SUCCESS_VIEW_TAB = "Viewing Tab: %1$s";
    public static final String MESSAGE_SUCCESS_VIEW_ALL = "Viewing all Tabs";

    private static final Map<String, Map<String, Integer>> moduleMap = ModTutGroup.getModuleMap();

    public final String moduleName;
    public final String tutorialName;
    public final String modTutGroup;

    private final boolean isAll;

    /**
     * Creates a ViewCommand to view the specified {@code ModTutGroup} tab
     */
    public ViewCommand(String modTutGroup, String moduleName, String tutorialName) {
        this.moduleName = moduleName;
        this.tutorialName = tutorialName;
        this.modTutGroup = modTutGroup;
        this.isAll = false;
    }

    /**
     * Creates a ViewCommand to view all
     */
    public ViewCommand() {
        this.moduleName = "";
        this.tutorialName = "";
        this.modTutGroup = "";
        this.isAll = true;
    }


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.isAll) {
            model.setViewAll(true);
            return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL);
        } else {
            model.setViewAll(false);

            if (!moduleMap.containsKey(moduleName)) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP, modTutGroup));
            } else {
                if (!moduleMap.get(moduleName).containsKey(tutorialName)) {
                    throw new CommandException(String.format(Messages.MESSAGE_INVALID_MODULE_TUTORIAL_GROUP,
                            modTutGroup));
                }
            }

            model.setSelectedTabs(moduleName, tutorialName);
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS_VIEW_TAB, modTutGroup),
                    true, false);
        }
    }
}
