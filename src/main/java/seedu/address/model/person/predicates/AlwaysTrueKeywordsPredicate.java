package seedu.address.model.person.predicates;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Predicate that always returns true.
 */
public class AlwaysTrueKeywordsPredicate extends FieldContainsKeywordsPredicate {

    public AlwaysTrueKeywordsPredicate() {
        super(new ArrayList<>());
    }
    @Override
    public boolean test(Person person) {
        return true;
    }
}
