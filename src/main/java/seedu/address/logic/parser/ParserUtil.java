package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Name;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @param oneBasedIndex the index as a string
     * @return the parsed {@code Index}
     * @throws ParseException if the specified index is invalid (not a non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param name the name as a string
     * @return the parsed {@code Name}
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param phone the phone number as a string
     * @return the parsed {@code Phone}
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param address the address as a string
     * @return the parsed {@code Address}
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param email the email address as a string
     * @return the parsed {@code Email}
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String telegramHandle} into a {@code TelegramHandle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param telegramHandle the Telegram handle as a string
     * @return the parsed {@code TelegramHandle}
     * @throws ParseException if the given {@code telegramHandle} is invalid.
     */
    public static TelegramHandle parseTelegramHandle(String telegramHandle) throws ParseException {
        requireNonNull(telegramHandle);
        String trimmedTelegramHandle = telegramHandle.trim();
        if (!TelegramHandle.isValidTelegramHandle(trimmedTelegramHandle)) {
            throw new ParseException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        return new TelegramHandle(trimmedTelegramHandle);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param tag the tag as a string
     * @return the parsed {@code Tag}
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String modTutGroup} into a {@code ModTutGroup}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param modTutGroup the ModTut group as a string
     * @return the parsed {@code ModTutGroup}
     * @throws ParseException if the given {@code modTutGroup} is invalid.
     */
    public static ModTutGroup parseModTutGroup(String modTutGroup) throws ParseException {
        requireNonNull(modTutGroup);
        String trimmedModTutGroup = modTutGroup.trim();
        if (!ModTutGroup.isValidModTutGroup(trimmedModTutGroup)) {
            throw new ParseException(ModTutGroup.MESSAGE_CONSTRAINTS);
        }
        return new ModTutGroup(trimmedModTutGroup);
    }

    /**
     * Parses a {@code Collection<String> modTutGroups} into a set of {@code ModTutGroup}.
     * Leading and trailing whitespaces for each group will be trimmed.
     *
     * @param modTutGroups the collection of ModTut groups as strings
     * @return the set of parsed {@code ModTutGroup}
     * @throws ParseException if any of the given {@code modTutGroups} are invalid.
     */
    public static Set<ModTutGroup> parseModTutGroups(Collection<String> modTutGroups) throws ParseException {
        requireNonNull(modTutGroups);
        final Set<ModTutGroup> modTutGroupSet = new HashSet<>();
        for (String modTutGroup : modTutGroups) {
            modTutGroupSet.add(parseModTutGroup(modTutGroup));
        }
        return modTutGroupSet;
    }
}
