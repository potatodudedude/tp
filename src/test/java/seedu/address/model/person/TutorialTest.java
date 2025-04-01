package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TutorialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tutorial(null));
    }

    @Test
    public void constructor_invalidTutorial_throwsAssertion() {
        assertThrows(AssertionError.class, () -> new Tutorial("T01-")); // contains special character
    }

    @Test
    public void getName_success() {
        Tutorial t01 = new Tutorial("T01");
        assertEquals("T01", t01.getName());
    }

    @Test
    public void isValidTutorial() {
        // null tutorial name
        assertThrows(NullPointerException.class, () -> Tutorial.isValidTutorial(null));

        // empty tutorial name -> false
        assertEquals(false, Tutorial.isValidTutorial("")); // empty string

        // invalid tutorial name -> false
        assertEquals(false, Tutorial.isValidTutorial("T01-")); // contains special character

        // valid tutorial name
        assertEquals(true, Tutorial.isValidTutorial("T01")); // contains only alphanumeric characters
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tutorial t01 = new Tutorial("T01");
        Tutorial anotherT01 = new Tutorial("T01");
        assertEquals(t01, anotherT01);

        // same object -> returns true
        assertEquals(t01, t01);

        // null -> returns false
        assertNotEquals(t01, null);

        // different types -> returns false
        assertNotEquals(t01, 5);

        // different values -> returns false
        Tutorial t02 = new Tutorial("T02");
        assertNotEquals(t01, t02);
    }
}
