package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String telegramHandle;
    private final String email;
    private final List<JsonAdaptedModTutGroup> modTutGroups = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isPin;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("telegramHandle") String telegramHandle,
                             @JsonProperty("email") String email,
                             @JsonProperty("modTutGroups") List<JsonAdaptedModTutGroup> modTutGroups,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("isPin") boolean isPin) {
        this.name = name;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.modTutGroups.addAll(modTutGroups);
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isPin = isPin;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     **/

    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        telegramHandle = source.getTelegramHandle().value;
        email = source.getEmail().value;
        modTutGroups.addAll(source.getModTutGroups().stream()
                .map(JsonAdaptedModTutGroup::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        isPin = source.getPin();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<ModTutGroup> personModTutGroups = new ArrayList<>();
        for (JsonAdaptedModTutGroup modTutGroup : modTutGroups) {
            personModTutGroups.add(modTutGroup.toModelType());
        }
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (telegramHandle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TelegramHandle.class.getSimpleName()));
        }
        if (!TelegramHandle.isValidTelegramHandle(telegramHandle)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final TelegramHandle modelTelegramHandle = new TelegramHandle(telegramHandle);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        if (personModTutGroups.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModTutGroup.class.getSimpleName()));
        }
        for (ModTutGroup modTutGroup : personModTutGroups) {
            if (!ModTutGroup.isValidModTutGroup(modTutGroup.toString())) {
                throw new IllegalValueException(ModTutGroup.MESSAGE_CONSTRAINTS);
            }
        }
        final Email modelEmail = new Email(email);
        final Set<ModTutGroup> modelTutGroups = new HashSet<>(personModTutGroups);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelTelegramHandle, modelEmail, modelTutGroups, modelTags, isPin);
    }

}
