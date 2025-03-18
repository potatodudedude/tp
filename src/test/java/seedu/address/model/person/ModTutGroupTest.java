package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModTutGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModTutGroup(null));
    }

    @Test
    public void constructor_invalidModTutGroup_throwsIllegalArgumentException() {
        String invalidModTutGroup = "";
        assertThrows(IllegalArgumentException.class, () -> new ModTutGroup(invalidModTutGroup));
    }

    @Test
    public void isValidModTutGroup() {
        // null modTutGroup
        assertThrows(NullPointerException.class, () -> ModTutGroup.isValidModTutGroup(null));

        // invalid modTutGroup
        assertFalse(ModTutGroup.isValidModTutGroup("")); // empty string
        assertFalse(ModTutGroup.isValidModTutGroup(" ")); // spaces only
        assertFalse(ModTutGroup.isValidModTutGroup("-")); // dash only
        assertFalse(ModTutGroup.isValidModTutGroup(" - ")); // dash with spaces
        assertFalse(ModTutGroup.isValidModTutGroup("CS1234")); // one part only
        assertFalse(ModTutGroup.isValidModTutGroup("CS1234 A10")); // contains space instead of dash
        assertFalse(ModTutGroup.isValidModTutGroup("CS1234-")); // without tutorial group
        assertFalse(ModTutGroup.isValidModTutGroup("-A10")); // without module
        assertFalse(ModTutGroup.isValidModTutGroup("C$123-A10")); // contains non-alphanumeric characters
        assertFalse(ModTutGroup.isValidModTutGroup("CS1234--A10")); // double dash
        assertFalse(ModTutGroup.isValidModTutGroup("CS1234-A10-")); // ends with dash

        // valid modTutGroup
        assertTrue(ModTutGroup.isValidModTutGroup("ABCD-XYZ")); // letters only
        assertTrue(ModTutGroup.isValidModTutGroup("1234-567")); // numbers only
        assertTrue(ModTutGroup.isValidModTutGroup("CS1234-A10")); // alphanumeric characters
    }

    @Test
    public void equals() {
        ModTutGroup modTutGroup = new ModTutGroup("CS1234-A10");

        // same values -> returns true
        assertTrue(modTutGroup.equals(new ModTutGroup("CS1234-A10")));

        // same object -> returns true
        assertTrue(modTutGroup.equals(modTutGroup));

        // null -> returns false
        assertFalse(modTutGroup.equals(null));

        // different types -> returns false
        assertFalse(modTutGroup.equals(5.0f));

        // different values -> returns false
        assertFalse(modTutGroup.equals(new ModTutGroup("CS1234-A11")));
    }
}
