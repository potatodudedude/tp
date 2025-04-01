package seedu.address.model.person;

/**
 * Represents a Person's tutorial in ConnectS.
 * Guarantees: immutable; is valid as declared in {@link ModTutGroup#isValidModTutGroup(String)}
 */
public class Tutorial {
    private final String value;

    public Tutorial(String name) {
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

        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return value.equalsIgnoreCase(otherTutorial.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
