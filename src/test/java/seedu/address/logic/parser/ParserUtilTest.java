package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.TelegramHandle;


public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TELEGRAMHANDLE = "telegram";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MOD = "CS2109_T12";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TELEGRAMHANDLE = "@telegram";
    private static final String VALID_MOD_1 = "CS2109S-T12";
    private static final String VALID_MOD_2 = "CS2103T-T01";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTelegramHandle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTelegramHandle((String) null));
    }

    @Test
    public void parseTelegramHandle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegramHandle(INVALID_TELEGRAMHANDLE));
    }

    @Test
    public void parseTelegramHandle_validValueWithoutWhitespace_returnsTelegramHandle() throws Exception {
        TelegramHandle expectedTelegramHandle = new TelegramHandle(VALID_TELEGRAMHANDLE);
        assertEquals(expectedTelegramHandle, ParserUtil.parseTelegramHandle(VALID_TELEGRAMHANDLE));
    }

    @Test
    public void parseTelegramHandle_validValueWithWhitespace_returnsTrimmedTelegramHandle() throws Exception {
        String TelegramHandleWithWhitespace = WHITESPACE + VALID_TELEGRAMHANDLE + WHITESPACE;
        TelegramHandle expectedTelegramHandle = new TelegramHandle(VALID_TELEGRAMHANDLE);
        assertEquals(expectedTelegramHandle, ParserUtil.parseTelegramHandle(TelegramHandleWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseModTutGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModTutGroup(null));
    }

    @Test
    public void parseModTutGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModTutGroup(INVALID_MOD));
    }

    @Test
    public void parseModTutGroup_validValueWithoutWhitespace_returnsModTutGroup() throws Exception {
        ModTutGroup expectedModTutGroup = new ModTutGroup(VALID_MOD_1);
        assertEquals(expectedModTutGroup, ParserUtil.parseModTutGroup(VALID_MOD_1));
    }

    @Test
    public void parseModTutGroup_validValueWithWhitespace_returnsTrimmedModTutGroup() throws Exception {
        String modTutGroupWithWhitespace = WHITESPACE + VALID_MOD_1 + WHITESPACE;
        ModTutGroup expectedModTutGroup = new ModTutGroup(VALID_MOD_1);
        assertEquals(expectedModTutGroup, ParserUtil.parseModTutGroup(modTutGroupWithWhitespace));
    }

    @Test
    public void parseModTutGroups_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModTutGroups(null));
    }

    @Test
    public void parseModTutGroups_collectionWithInvalidModTutGroups_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModTutGroups(Arrays.asList(VALID_MOD_1, INVALID_MOD)));
    }

    @Test
    public void parseModTutGroups_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseModTutGroups(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseModTutGroups_collectionWithValidModTutGroups_returnsModTutGroupSet() throws Exception {
        Set<ModTutGroup> actualModTutGroupSet = ParserUtil.parseModTutGroups(Arrays.asList(VALID_MOD_1, VALID_MOD_2));
        Set<ModTutGroup> expectedModTutGroupSet = new HashSet<ModTutGroup>(Arrays.asList(new ModTutGroup(VALID_MOD_1), new ModTutGroup(VALID_MOD_2)));

        assertEquals(expectedModTutGroupSet, actualModTutGroupSet);
    }
}
