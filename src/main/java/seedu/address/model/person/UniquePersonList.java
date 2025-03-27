package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMustBeSameException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.WrongPinStatusException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Sorts the list using the given {@code comparator}.
     */
    public void sort(Comparator<Person> comparator) {
        requireNonNull(comparator);
        // Remove pinned persons from the list before sorting, then add the temp list back to the front of the list
        List<Person> temporaryList = internalList.stream().filter(Person::getPin).toList();
        internalList.removeAll(temporaryList);
        internalList.sort(comparator);
        internalList.addAll(0, temporaryList);
    }

    /**
     * Replaces the person {@code target} in the list with {@code pinnedPerson},
     * and pins the person to the top of the list.
     */
    public void pin(Person target, Person pinnedPerson) {
        requireAllNonNull(target, pinnedPerson);
        if (!pinnedPerson.getPin()) {
            throw new WrongPinStatusException();
        }
        setPersonIfSamePersonWithDifferentPin(target, pinnedPerson);
        internalList.remove(pinnedPerson);
        internalList.add(0, pinnedPerson);
    }

    /**
     * Replaces the {@code target} in the list with {@code unpinnedPerson}, and unpins the person.
     * The person is then put at the bottom of the list.
     */
    public void unpin(Person target, Person unpinnedPerson) {
        requireAllNonNull(target, unpinnedPerson);
        if (unpinnedPerson.getPin()) {
            throw new WrongPinStatusException();
        }
        setPersonIfSamePersonWithDifferentPin(target, unpinnedPerson);
        internalList.remove(unpinnedPerson);
        internalList.add(unpinnedPerson);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Replaces the person {@code target} in the list with {@code otherPerson}
     * if they have the same identity but with different pin status.
     */
    private void setPersonIfSamePersonWithDifferentPin(Person target, Person otherPerson) {
        requireAllNonNull(target, otherPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        if (!target.isSamePerson(otherPerson)) {
            throw new PersonMustBeSameException();
        }
        if (target.getPin() == otherPerson.getPin()) {
            throw new WrongPinStatusException();
        }

        internalList.set(index, otherPerson);
    }
}
