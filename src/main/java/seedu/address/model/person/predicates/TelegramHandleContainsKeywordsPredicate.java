package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code TelegramHandle} matches any of the keywords given.
 */
public class TelegramHandleContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {

    public TelegramHandleContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return super.keywords.stream()
                .anyMatch(keyword -> StringUtil.containsAnyIgnoreCase(person.getTelegramHandle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramHandleContainsKeywordsPredicate)) {
            return false;
        }

        TelegramHandleContainsKeywordsPredicate otherTelegramHandleContainsKeywordsPredicate =
                (TelegramHandleContainsKeywordsPredicate) other;
        return keywords.equals(otherTelegramHandleContainsKeywordsPredicate.keywords);
    }
}
