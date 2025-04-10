package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.FieldContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names/telegram handle/email "
            + "contain any of the specified keywords (case-insensitive) "
            + "and filters them out.\n"
            + "Only one field can be searched at a time.\n"
            + "Parameters: "
            + "<" + PREFIX_NAME + "NAME_KEYWORD(S)... "
            + PREFIX_TELEGRAM + "TELEGRAM_HANDLE_KEYWORD(S)... "
            + PREFIX_EMAIL + "EMAIL_KEYWORD(S)...>\n"
            + "Example: " + COMMAND_WORD + " n/alice bob" + "\nOR " + COMMAND_WORD + " t/dav koh";
    public static final String MESSAGE_EMPTY_NAME = "The name that you gave was empty.";
    public static final String MESSAGE_EMPTY_TELEGRAMHANDLE = "The telegram handle that you gave was empty.";
    public static final String MESSAGE_EMPTY_EMAIL = "The email that you gave was empty.";
    private FieldContainsKeywordsPredicate predicate;

    public FindCommand(FieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (!model.getFilteredPersonList().isEmpty()) {
            Person firstPerson = model.getFilteredPersonList().get(0);
            ModTutGroup firstModTutGroup = firstPerson.getModTutGroups().iterator().next();
            String moduleName = firstModTutGroup.getModule().getName();
            String tutorialName = firstModTutGroup.getTutorial().getName();
            model.setSelectedTabs(moduleName, tutorialName);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
