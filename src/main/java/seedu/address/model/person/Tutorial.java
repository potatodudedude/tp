package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's tutorial in ConnectS.
 * Guarantees: immutable; is valid as declared in {@link ModTutGroup#isValidModTutGroup(String)}
 * and asserted in constructor.
 */
public class Tutorial {
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private final String name;

    /**
     * Constructs a {@code Tutorial} with given {@code name}.
     */
    public Tutorial(String name) {
        requireNonNull(name);
        assert isValidTutorial(name) : "Tutorial name should be alphanumeric";
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
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

        return name.equalsIgnoreCase(otherTutorial.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
