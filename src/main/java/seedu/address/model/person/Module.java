package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Person's module in ConnectS.
 * Guarantees: immutable; is valid as declared in {@link ModTutGroup#isValidModTutGroup(String)}
 * and asserted in constructor
 */
public class Module {
    public static final String MESSAGE_CONSTRAINTS = "Module should consist of one or more alphanumeric characters";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private final String name;

    /**
     * Constructs a {@code Module} with given {@code name}.
     */
    public Module(String name) {
        requireNonNull(name);
        assert isValidModule(name) : "Module name should be alphanumeric";
        assert StringUtil.isUpperCase(name) : "Module name should be in uppercase";
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;

        return name.equalsIgnoreCase(otherModule.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
