package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TelegramHandleContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate =
                new TelegramHandleContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);

        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(secondPredicateKeywordList);
        TelegramHandleContainsKeywordsPredicate secondTelegramHandlePredicate =
                new TelegramHandleContainsKeywordsPredicate(secondPredicateKeywordList);
        EmailContainsKeywordsPredicate secondEmailPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(namePredicate.equals(namePredicate));
        assertTrue(emailPredicate.equals(emailPredicate));
        assertTrue(telegramHandlePredicate.equals(telegramHandlePredicate));

        // same values and predicate type -> returns true
        NameContainsKeywordsPredicate namePredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(namePredicate.equals(namePredicateCopy));
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicateCopy =
                new TelegramHandleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(telegramHandlePredicate.equals(telegramHandlePredicateCopy));
        EmailContainsKeywordsPredicate emailPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(emailPredicate.equals(emailPredicateCopy));

        // different types -> returns false
        assertFalse(namePredicate.equals(1));
        assertFalse(namePredicate.equals(telegramHandlePredicate));
        assertFalse(telegramHandlePredicate.equals(emailPredicate));
        assertFalse(emailPredicate.equals(namePredicate));

        // null -> returns false
        assertFalse(namePredicate.equals(null));
        assertFalse(telegramHandlePredicateCopy.equals(null));
        assertFalse(emailPredicate.equals(null));

        // different person -> returns false
        assertFalse(namePredicate.equals(secondNamePredicate));
        assertFalse(telegramHandlePredicate.equals(secondTelegramHandlePredicate));
        assertFalse(emailPredicate.equals(secondEmailPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate =
                new TelegramHandleContainsKeywordsPredicate(Collections.emptyList());
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(Collections.emptyList());

        assertFalse(namePredicate.test(new PersonBuilder().withName("Alice").build()));
        assertFalse(telegramHandlePredicate.test(new PersonBuilder().withTele("@alice").build()));
        assertFalse(emailPredicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        telegramHandlePredicate = new TelegramHandleContainsKeywordsPredicate(Arrays.asList("@carol"));
        emailPredicate = new EmailContainsKeywordsPredicate(Arrays.asList("carol@example.com"));

        assertFalse(namePredicate.test(new PersonBuilder().withName("Alice Bob").build()));
        assertFalse(telegramHandlePredicate.test(new PersonBuilder().withTele("@alice").build()));
        assertFalse(emailPredicate.test(new PersonBuilder().withEmail("bob@example.com").build()));


        // Keywords match other fields but not its own
        namePredicate = new NameContainsKeywordsPredicate(Arrays
                .asList("12345", "alice@email.com", "@alice", "CS1234-A10"));
        assertFalse(namePredicate.test(new PersonBuilder().withName("Alicia")
                .withEmail("alice@email.com").withTele("@alice")
                .withModTuts("CS1234-A10").build()));

        telegramHandlePredicate = new TelegramHandleContainsKeywordsPredicate(Arrays
                .asList("Alicia", "alice@email.com", "@12345", "CS1234-A10"));
        assertFalse(telegramHandlePredicate.test(new PersonBuilder().withName("Alicia")
                .withEmail("alice@email.com").withTele("@alice")
                .withModTuts("CS1234-A10").build()));

        emailPredicate = new EmailContainsKeywordsPredicate(Arrays
                .asList("Alicia", "12345@email.com", "@alice", "CS1234-A10"));
        assertFalse(emailPredicate.test(new PersonBuilder().withName("Alicia")
                .withEmail("alice@email.com").withTele("@alice")
                .withModTuts("CS1234-A10").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

