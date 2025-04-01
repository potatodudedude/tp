package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Abstract class for testing that a {@code Person}'s field matches any of the keywords given.
 */
public abstract class FieldContainsKeywordsPredicate implements Predicate<Person> {
    protected final List<String> keywords;

    public FieldContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public abstract boolean test(Person person);

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
