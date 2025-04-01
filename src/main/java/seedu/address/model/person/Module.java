package seedu.address.model.person;

/**
 * Represents a Person's module in ConnectS.
 * Guarantees: immutable; is valid as declared in {@link ModTutGroup#isValidModTutGroup(String)}
 */
public class Module {
    private final String value;

    public Module(String name) {
        value = name;
    }

    public String toString() {
        return value;
    }

    public String getName() {
        return value;
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
        return value.equalsIgnoreCase(otherModule.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
