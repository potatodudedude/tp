package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getModTutGroups().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same telegram handle, all other attributes different -> returns true
        Person editedAlice2 = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withEmail(VALID_EMAIL_BOB).withModTuts(VALID_MODTUT_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice2));

        // same email, all other attributes different -> return true
        Person editedAlice3 = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice3));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tele -> returns false
        editedAlice = new PersonBuilder(ALICE).withTele(VALID_TELE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different modTut -> returns false
        editedAlice = new PersonBuilder(ALICE).withModTuts(VALID_MODTUT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", telegramHandle=" + ALICE.getTelegramHandle() + ", email=" + ALICE.getEmail()
                + ", modTutGroups=" + ALICE.getModTutGroups() + ", tags=" + ALICE.getTags() + "}";
        String actual = ALICE.toString();
        assertEquals(expected, ALICE.toString());
    }
}
