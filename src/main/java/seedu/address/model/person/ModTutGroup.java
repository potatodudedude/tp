package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's module - tutorial group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModTutGroup(String)} (String)}
 */
public class ModTutGroup {

    public static final String MESSAGE_CONSTRAINTS = "Specify the module and tutorial group with a dash in between";
    private static final String VALIDATION_REGEX = "^[A-Za-z0-9]+-[A-Za-z0-9]+$";

    public final String value;

    /**
     * Constructs a module - tutorial group.
     *
     * @param modTutGroup valid module - tutorial group.
     */
    public ModTutGroup(String modTutGroup) {
        requireNonNull(modTutGroup);
        checkArgument(isValidModTutGroup(modTutGroup), MESSAGE_CONSTRAINTS);
        value = modTutGroup;
    }

    /**
     * Returns if a given string is a valid module - tutorial group.
     */
    public static boolean isValidModTutGroup(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModTutGroup)) {
            return false;
        }

        ModTutGroup otherModTutGroup = (ModTutGroup) other;
        return value.equals(otherModTutGroup.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
