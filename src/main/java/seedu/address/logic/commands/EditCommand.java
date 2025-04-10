package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing person in ConnectS.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "{" + PREFIX_NAME + "NAME "
            + PREFIX_TELEGRAM + "TELEGRAM_HANDLE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MOD + "MODULE-TUTORIAL_GROUP... "
            + PREFIX_TAG + "TAG...}\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TELEGRAM + "@johndoe "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in ConnectS.";
    public static final String MESSAGE_MOD_CANNOT_BE_EMPTY = "Module-Tutorial group cannot be empty.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        TelegramHandle updatedTelegramHandle = editPersonDescriptor.getTelegramHandle()
                .orElse(personToEdit.getTelegramHandle());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Set<ModTutGroup> updatedModTutGroups = editPersonDescriptor.getModTutGroup()
                .orElse(personToEdit.getModTutGroups());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        boolean isPin = personToEdit.getPin(); // pin status should not be edited
        return new Person(updatedName, updatedTelegramHandle, updatedEmail, updatedModTutGroups, updatedTags, isPin);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> filteredList;
        if (model.isViewAll()) {
            filteredList = model.getFilteredPersonList();
        } else {
            filteredList = model.getCurrentTabPersonList();
        }

        if (index.getZeroBased() >= filteredList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = filteredList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (model.hasEditedPerson(personToEdit, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (editPersonDescriptor.getModTutGroup().isPresent()) {
            Set<ModTutGroup> oldModTutGroup = personToEdit.getModTutGroups();

            for (ModTutGroup modTutGroup : oldModTutGroup) {
                ModTutGroup.decreaseTutorialCount(modTutGroup.toString());
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private TelegramHandle telegramHandle;
        private Email email;
        private Set<ModTutGroup> modTutGroup;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setTelegramHandle(toCopy.telegramHandle);
            setModTutGroup(toCopy.modTutGroup);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, telegramHandle, email, modTutGroup, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<TelegramHandle> getTelegramHandle() {
            return Optional.ofNullable(telegramHandle);
        }

        public void setTelegramHandle(TelegramHandle telegramHandle) {
            this.telegramHandle = telegramHandle;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        /**
         * Returns an unmodifiable modTutGroup set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code modTutGroups} is null.
         */
        public Optional<Set<ModTutGroup>> getModTutGroup() {
            return (modTutGroup != null) ? Optional.of(Collections.unmodifiableSet(modTutGroup)) : Optional.empty();
        }

        /**
         * Sets {@code modTutGroups} to this object's {@code modTutGroups}.
         * A defensive copy of {@code modTutGroups} is used internally.
         */
        public void setModTutGroup(Set<ModTutGroup> modTutGroup) {
            this.modTutGroup = (modTutGroup != null) ? new HashSet<>(modTutGroup) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(telegramHandle, otherEditPersonDescriptor.telegramHandle)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(modTutGroup, otherEditPersonDescriptor.modTutGroup)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("telegramHandle", telegramHandle)
                    .add("email", email)
                    .add("modTutGroup", modTutGroup)
                    .add("tags", tags)
                    .toString();
        }
    }
}
