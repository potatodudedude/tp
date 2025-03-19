package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_TELEGRAM + person.getTelegramHandle().value + " ");
        sb.append(PREFIX_MOD);
        person.getModTutGroups().forEach(m -> sb.append(m.value + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getTelegramHandle().ifPresent(telegramHandle -> sb.append(PREFIX_TELEGRAM)
                .append(telegramHandle.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        if (descriptor.getModTutGroup().isPresent()) {
            Set<ModTutGroup> modTutGroups = descriptor.getModTutGroup().get();
            if (modTutGroups.isEmpty()) {
                sb.append(PREFIX_MOD);
            } else {
                modTutGroups.forEach(s -> sb.append(PREFIX_MOD).append(s.value).append(" "));
            }
        }
        return sb.toString();
    }
}
