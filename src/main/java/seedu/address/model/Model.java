package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' ConnectS file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' ConnectS file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces ConnectS data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in ConnectS.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if there's a person with the same identity as {@code editedPerson}, excluding {@code personToEdit}.
     */

    boolean hasEditedPerson(Person personToEdit, Person editedPerson);

    /**
     * Deletes the given tutorial group and people who takes only that tutorial group.
     */
    void deleteModTut(ModTutGroup modTutGroup);

    /**
     * Deletes the given module and people who takes only that module.
     */
    void deleteMod(Module module);

    /**
     * Deletes the given person.
     * The person must exist in ConnectS.
     */

    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in ConnectS.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in ConnectS.
     * The person identity of {@code editedPerson} must not be the same as another existing person in ConnectS.
     */
    void setPerson(Person target, Person editedPerson);


    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered person list in tab view */
    ObservableList<Person> getCurrentTabPersonList();

    /**
     * Sorts the address book by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortAddressBook(Comparator<Person> comparator);

    /**
     * Replaces the given person {@code target} with {@code pinnedPerson},
     * and pins the {@code pinnedPerson} to the top of the list.
     * <ul>
     *     <li>{@code target} must exist in ConnectS.</li>
     *     <li>{@code pinnedPerson} must have the same identity as {@code target}.</li>
     * </ul>
     */
    void pinPerson(Person target, Person pinnedPerson);

    /**
     * Replaces the given person {@code target} with {@code unpinnedPerson},
     * and adds the {@code unpinnedPerson} to the end of the list.
     * <ul>
     *     <li>{@code target} must exist in ConnectS.</li>
     *     <li>{@code unpinnedPerson} must have the same identity as {@code target}.</li>
     * </ul>
     */
    void unpinPerson(Person target, Person unpinnedPerson);

    /**
     * Returns true if view all option is set to "All"
     */
    boolean isViewAll();

    /**
     * @param isViewAll true if view all option is "All", false if option is "Tabs"
     */
    void setViewAll(boolean isViewAll);

    /**
     * Returns a list containing the names of the selected module and tutorial tab
     */
    List<String> getSelectedTabs();

    /**
     * @param selectedModuleTab the name of the selected module tab
     * @param selectedTutorialTab the name of the selected tutorial tab
     */
    void setSelectedTabs(String selectedModuleTab, String selectedTutorialTab);
}
