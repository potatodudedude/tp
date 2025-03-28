package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.TelegramHandle;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TELEGRAMHANDLE = "telegram";
    private static final String INVALID_MODTUTGROUP = "CS2103T T21";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_TELEGRAMHANDLE = BENSON.getTelegramHandle().toString();
    private static final List<JsonAdaptedModTutGroup> VALID_MODTUTGROUP = BENSON.getModTutGroups().stream()
            .map(JsonAdaptedModTutGroup::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    // Pin is boolean, that is either true or false, thus it is always valid
    private static final boolean VALID_PIN = BENSON.getPin();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_TELEGRAMHANDLE, VALID_EMAIL, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_TELEGRAMHANDLE, VALID_EMAIL, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_TELEGRAMHANDLE, VALID_EMAIL, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = TelegramHandle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TelegramHandle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAMHANDLE, INVALID_EMAIL, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAMHANDLE, null, VALID_MODTUTGROUP, VALID_TAGS, VALID_PIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidModTutGroup_throwsIllegalValueException() {
        List<JsonAdaptedModTutGroup> invalidModTutGroup = new ArrayList<>(VALID_MODTUTGROUP);
        invalidModTutGroup.add(new JsonAdaptedModTutGroup(INVALID_MODTUTGROUP));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAMHANDLE, VALID_EMAIL, invalidModTutGroup, VALID_TAGS, VALID_PIN);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullModTutGroup_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAMHANDLE, VALID_EMAIL, null, VALID_TAGS, VALID_PIN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "ModTutGroup");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
