package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_EMPTY_INDEX = "The person index provided is empty";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MORE_THAN_ONE_FIELD = "Only one field is allowed for this command.";
    public static final String MESSAGE_INVALID_MODULE_TUTORIAL_GROUP = "No such Module-Tutorial Group: %1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName()).append("\n")
                .append("Telegram Handle: ").append(person.getTelegramHandle()).append("\n")
                .append("Email: ").append(person.getEmail()).append("\n")
                .append("Module Tutorial Group(s): ");
        ModTutGroup[] modTutGroups = person.getModTutGroups().toArray(new ModTutGroup[0]);
        for (int i = 0; i < modTutGroups.length; i++) {
            builder.append(modTutGroups[i]);
            if (i < modTutGroups.length - 1) {
                builder.append(", ");
            }
        }
        if (!person.getTags().isEmpty()) {
            builder.append("\nTag(s): ");
            Tag[] tags = person.getTags().toArray(new Tag[0]);
            for (int i = 0; i < tags.length; i++) {
                builder.append(tags[i]);
                if (i < tags.length - 1) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }

}
