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

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String telegramHandle;
    private final String email;
    private final List<JsonAdaptedModTutGroup> modTutGroups = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("telegramHandle") String telegramHandle,
            @JsonProperty("email") String email,
            @JsonProperty("modTutGroups") List<JsonAdaptedModTutGroup> modTutGroups) {
        this.name = name;
        this.telegramHandle = telegramHandle;
        this.email = email;
        if (modTutGroups != null) {
            this.modTutGroups.addAll(modTutGroups);
        }
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
        final Email modelEmail = new Email(email);
        final Set<ModTutGroup> modelTutGroups = new HashSet<>(personModTutGroups);
        return new Person(modelName, modelEmail, modelTelegramHandle, modelTutGroups);
    }

}
