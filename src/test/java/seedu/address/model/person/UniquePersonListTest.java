package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.PINNED_ALICE;
import static seedu.address.testutil.TypicalPersons.PINNED_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMustBeSameException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.WrongPinStatusException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void sort_nullComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.sort(null));
    }

    @Test
    public void sort_sortsList() {
        uniquePersonList.add(BOB);
        uniquePersonList.add(ALICE);
        uniquePersonList.sort(SortCommand.COMPARATOR);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void pin_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.pin(null, null));
        assertThrows(NullPointerException.class, () -> uniquePersonList.pin(ALICE, null));
        assertThrows(NullPointerException.class, () -> uniquePersonList.pin(null, ALICE));
    }

    @Test
    public void pin_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.pin(ALICE, PINNED_ALICE));
    }

    @Test
    public void pin_differentPerson_throwsPersonMustBeSameException() {
        uniquePersonList.add(ALICE);
        assertThrows(PersonMustBeSameException.class, () -> uniquePersonList.pin(ALICE, PINNED_BOB));
    }

    @Test
    public void pin_samePersonWithDifferentPinStatus_success() {
        uniquePersonList.setPersons(new ArrayList<>(Arrays.asList(ALICE, BOB)));
        uniquePersonList.pin(BOB, PINNED_BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(new ArrayList<>(Arrays.asList(PINNED_BOB, ALICE)));
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void pin_samePersonWithSamePinStatus_throwsWrongPinStatusException() {
        uniquePersonList.add(PINNED_ALICE);
        assertThrows(WrongPinStatusException.class, () -> uniquePersonList.pin(PINNED_ALICE, PINNED_ALICE));
    }

    @Test
    public void pin_alreadyPinnedPerson_throwsWrongPinStatusException() {
        uniquePersonList.add(PINNED_ALICE);
        assertThrows(WrongPinStatusException.class, () -> uniquePersonList.pin(PINNED_ALICE, PINNED_ALICE));
    }

    @Test
    public void unpin_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.unpin(null, null));
        assertThrows(NullPointerException.class, () -> uniquePersonList.unpin(ALICE, null));
        assertThrows(NullPointerException.class, () -> uniquePersonList.unpin(null, ALICE));
    }

    @Test
    public void unpin_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.unpin(ALICE, ALICE));
    }

    @Test
    public void unpin_differentPerson_throwsPersonMustBeSameException() {
        uniquePersonList.add(PINNED_ALICE);
        assertThrows(PersonMustBeSameException.class, () -> uniquePersonList.unpin(PINNED_ALICE, BOB));
    }

    @Test
    public void unpin_samePersonWithDifferentPinStatus_success() {
        uniquePersonList.setPersons(new ArrayList<>(Arrays.asList(PINNED_BOB, ALICE)));
        uniquePersonList.unpin(PINNED_BOB, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(new ArrayList<>(Arrays.asList(ALICE, BOB)));
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void unpin_samePersonWithSamePinStatus_throwsWrongPinStatusException() {
        uniquePersonList.add(PINNED_BOB);
        assertThrows(WrongPinStatusException.class, () -> uniquePersonList.unpin(PINNED_BOB, PINNED_BOB));
    }

    @Test
    public void unpin_alreadyUnpinnedPerson_throwsWrongPinStatusException() {
        uniquePersonList.add(ALICE);
        assertThrows(WrongPinStatusException.class, () -> uniquePersonList.unpin(ALICE, ALICE));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }
}

