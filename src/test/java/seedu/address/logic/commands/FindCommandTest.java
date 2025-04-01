package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TelegramHandleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate =
                new TelegramHandleContainsKeywordsPredicate(Collections.singletonList("first"));

        FindCommand findFirstNameCommand = new FindCommand(firstNamePredicate);
        FindCommand findSecondNameCommand = new FindCommand(secondNamePredicate);
        FindCommand findTelegramHandleCommand = new FindCommand(telegramHandlePredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));

        // same values same predicate type -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNamePredicate);
        assertTrue(findFirstNameCommand.equals(findFirstCommandCopy));

        // same values different predicate types -> returns false
        assertFalse(findFirstNameCommand.equals(findTelegramHandleCommand));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        // name predicate
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        FindCommand nameCommand = new FindCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(nameCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // telegram Handle predicate
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate = prepareTelegramHandlePredicate(" ");
        FindCommand telegramHandleCommand = new FindCommand(telegramHandlePredicate);
        expectedModel.updateFilteredPersonList(telegramHandlePredicate);
        assertCommandSuccess(telegramHandleCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // email predicate
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(" ");
        FindCommand emailCommand = new FindCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        // name predicate
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand nameCommand = new FindCommand(namePredicate);
        expectedModel.updateFilteredPersonList(namePredicate);
        assertCommandSuccess(nameCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // telegram Handle predicate
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate = prepareTelegramHandlePredicate(
                "@carl_kurz Meyer @fio");
        FindCommand telegramHandleCommand = new FindCommand(telegramHandlePredicate);
        expectedModel.updateFilteredPersonList(telegramHandlePredicate);
        assertCommandSuccess(telegramHandleCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // email predicate
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate("heinz werner lydia");
        FindCommand emailCommand = new FindCommand(emailPredicate);
        expectedModel.updateFilteredPersonList(emailPredicate);
        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        // name predicate
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand nameCommand = new FindCommand(namePredicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + namePredicate + "}";
        assertEquals(expected, nameCommand.toString());

        // telegram Handle predicate
        TelegramHandleContainsKeywordsPredicate telegramHandlePredicate =
                new TelegramHandleContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand telegramHandleCommand = new FindCommand(telegramHandlePredicate);
        expected = FindCommand.class.getCanonicalName() + "{predicate=" + telegramHandlePredicate + "}";
        assertEquals(expected, telegramHandleCommand.toString());

        // email predicate
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand emailCommand = new FindCommand(emailPredicate);
        expected = FindCommand.class.getCanonicalName() + "{predicate=" + emailPredicate + "}";
        assertEquals(expected, emailCommand.toString());

    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TelegramHandleContainsKeywordsPredicate}.
     */
    private TelegramHandleContainsKeywordsPredicate prepareTelegramHandlePredicate(String userInput) {
        return new TelegramHandleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
