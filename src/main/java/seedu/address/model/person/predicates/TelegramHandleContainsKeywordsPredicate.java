package seedu.address.model.person.predicates;

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
        return super.keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTelegramHandle().value, keyword));
    }

}
