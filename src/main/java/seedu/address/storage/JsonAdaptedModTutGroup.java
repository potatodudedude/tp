package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ModTutGroup;

/**
 * Jackson-friendly version of {@link ModTutGroup}.
 */
class JsonAdaptedModTutGroup {

    private final String modTutGroupName;

    /**
     * Constructs a {@code JsonAdaptedModTutGroup} with the given {@code modTutGroupName}.
     */
    @JsonCreator
    public JsonAdaptedModTutGroup(String modTutGroupName) {
        this.modTutGroupName = modTutGroupName;
    }

    /**
     * Converts a given {@code ModTutGroup} into this class for Jackson use.
     */
    public JsonAdaptedModTutGroup(ModTutGroup source) {
        modTutGroupName = source.value;
    }

    @JsonValue
    public String getModTutGroupName() {
        return modTutGroupName;
    }

    /**
     * Converts this Jackson-friendly adapted modTutGroup object into the model's {@code ModTutGroup} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted modTutGroup.
     */
    public ModTutGroup toModelType() throws IllegalValueException {
        if (!ModTutGroup.isValidModTutGroup(modTutGroupName)) {
            throw new IllegalValueException(ModTutGroup.MESSAGE_CONSTRAINTS);
        }
        return new ModTutGroup(modTutGroupName);
    }

}
