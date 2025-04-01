package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidModule_throwsAssertion() {
        assertThrows(AssertionError.class, () -> new Module("CS2103T-")); // contains special character
    }

    @Test
    public void getName_success() {
        Module cs2103t = new Module("CS2103T");
        assertEquals("CS2103T", cs2103t.getName());
    }

    @Test
    public void isValidModule() {
        // null module name
        assertThrows(NullPointerException.class, () -> Module.isValidModule(null));

        // empty module name -> false
        assertEquals(false, Module.isValidModule("")); // empty string

        // invalid module name -> false
        assertEquals(false, Module.isValidModule("CS2103T-")); // contains special character

        // valid module name
        assertEquals(true, Module.isValidModule("CS2103T")); // contains only alphanumeric characters
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module cs2103t = new Module("CS2103T");
        Module anotherCs2103t = new Module("CS2103T");
        assertEquals(cs2103t, anotherCs2103t);

        // same object -> returns true
        assertEquals(cs2103t, cs2103t);

        // null -> returns false
        assertNotEquals(cs2103t, null);

        // different types -> returns false
        assertNotEquals(cs2103t, 5);

        // different values -> returns false
        Module cs2101 = new Module("CS2101");
        assertNotEquals(cs2103t, cs2101);
    }
}
