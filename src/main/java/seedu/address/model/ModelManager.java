package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Represents the in-memory model of ConnectS data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final List<String> selectedTabs;
    private boolean isViewAll;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with ConnectS: " + addressBook + " and user prefs " + userPrefs);

        this.isViewAll = false;
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        selectedTabs = new ArrayList<>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }


    @Override
    public void deleteMod(Module module) {
        requireNonNull(module);

        List<Person> personsToRemove = new ArrayList<>();
        List<Person> personsToUpdate = new ArrayList<>();

        for (Person person : addressBook.getPersonList()) {
            Set<ModTutGroup> originalGroups = person.getModTutGroups();

            // Find all groups belonging to the specified module
            Set<ModTutGroup> groupsToKeep = originalGroups.stream()
                    .filter(group -> !group.getModule().equals(module))
                    .collect(Collectors.toSet());

            if (groupsToKeep.isEmpty() && !originalGroups.isEmpty()) {
                // All groups were from the target module → delete person
                personsToRemove.add(person);
            } else if (groupsToKeep.size() < originalGroups.size()) {
                // Person had a mix of target + other modules → update person
                Person updatedPerson = person.withUpdatedModTutGroups(groupsToKeep);
                personsToUpdate.add(updatedPerson);
            }
        }

        // Perform deletion
        for (Person person : personsToRemove) {
            addressBook.removePerson(person);
        }

        // Perform updates
        for (Person updatedPerson : personsToUpdate) {
            // Find the original person
            Person originalPerson = addressBook.getPersonList().stream()
                    .filter(p -> p.isSamePerson(updatedPerson))
                    .findFirst()
                    .orElseThrow(); // Should never happen

            addressBook.setPerson(originalPerson, updatedPerson);
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }


    @Override
    public void deleteModTut(ModTutGroup modTutGroup) {
        requireNonNull(modTutGroup);

        List<Person> personsToRemove = new ArrayList<>();
        List<Person> personsToUpdate = new ArrayList<>();

        for (Person person : addressBook.getPersonList()) {
            Set<ModTutGroup> originalGroups = person.getModTutGroups();

            if (!originalGroups.contains(modTutGroup)) {
                continue; // Person doesn't have this group, skip
            }

            Set<ModTutGroup> groupsToKeep = originalGroups.stream()
                    .filter(group -> !group.equals(modTutGroup))
                    .collect(Collectors.toSet());

            if (groupsToKeep.isEmpty()) {
                // This was their only group → delete
                personsToRemove.add(person);
            } else {
                // Person had other groups too → update
                Person updatedPerson = person.withUpdatedModTutGroups(groupsToKeep);
                personsToUpdate.add(updatedPerson);
            }
        }

        // Delete persons
        for (Person person : personsToRemove) {
            addressBook.removePerson(person);
        }

        // Update persons
        for (Person updatedPerson : personsToUpdate) {
            Person originalPerson = addressBook.getPersonList().stream()
                    .filter(p -> p.isSamePerson(updatedPerson))
                    .findFirst()
                    .orElseThrow(); // Should never happen

            addressBook.setPerson(originalPerson, updatedPerson);
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasEditedPerson(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);
        return addressBook.hasEditedPerson(personToEdit, editedPerson);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void sortAddressBook(Comparator<Person> comparator) {
        requireNonNull(comparator);
        addressBook.sort(comparator);
    }

    @Override
    public void pinPerson(Person target, Person pinnedPerson) {
        requireNonNull(target);
        addressBook.pin(target, pinnedPerson);
    }

    @Override
    public void unpinPerson(Person target, Person unpinnedPerson) {
        requireNonNull(target);
        addressBook.unpin(target, unpinnedPerson);
    }

    @Override
    public boolean isViewAll() {
        return isViewAll;
    }

    @Override
    public void setViewAll(boolean isViewAll) {
        this.isViewAll = isViewAll;
    }

    @Override
    public List<String> getSelectedTabs() {
        return selectedTabs;
    }

    @Override
    public void setSelectedTabs(String selectedModuleTab, String selectedTutorialTab) {
        selectedTabs.clear();
        selectedTabs.add(selectedModuleTab);
        selectedTabs.add(selectedTutorialTab);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public ObservableList<Person> getCurrentTabPersonList() {
        assert (!isViewAll());
        List<Person> personList = this.getAddressBook().getPersonList();
        if (personList.isEmpty()) {
            return FXCollections.emptyObservableList();
        }

        List<String> selectedTabs = this.getSelectedTabs();
        String moduleName = selectedTabs.get(0);
        String tutorialName = selectedTabs.get(1);

        return personList.stream()
                .filter(p -> {
                    Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                    Stream<Module> moduleStream = modTutGroups.stream().map(ModTutGroup::getModule);
                    return moduleStream.anyMatch(m -> m.getName().equals(moduleName));
                })
                .filter(p -> {
                    Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                    Stream<Tutorial> tutorialStream = modTutGroups.stream().map(ModTutGroup::getTutorial);
                    return tutorialStream.anyMatch(m -> m.getName().equals(tutorialName));
                }).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
