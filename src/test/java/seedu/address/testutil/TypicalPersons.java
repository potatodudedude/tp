package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODTUT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withTele("@alice")
            .withModTuts("CS1234-A10").withPin(false).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withTele("@benson")
            .withModTuts("CS1234-A10").withPin(false).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").withTele("@carl_kurz")
            .withModTuts("CS1234-A10").withPin(false).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withTele("@daniel")
            .withModTuts("CS1234-A10").withPin(false).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withTele("@elleMeyer")
            .withModTuts("ES2666-Z01").withPin(false).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withTele("@fiona")
            .withModTuts("ES2666-Z01").withPin(false).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("anna@example.com").withTele("@george")
            .withModTuts("ES2666-Z01").withPin(false).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withTele("@hoon_Meier")
            .withEmail("stefan@example.com").withModTuts("CS1234-A10").withPin(false).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withTele("@ida_Mueller")
            .withEmail("hans@example.com").withModTuts("CS1234-A10").withPin(false).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
            .withTele(VALID_TELE_AMY).withModTuts(VALID_MODTUT_AMY).withPin(false).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
            .withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).withPin(false).build();

    // Manually added - Pinned Persons
    public static final Person PINNED_ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withTele("@alice")
            .withModTuts("CS1234-A10").withPin(true).build();
    public static final Person PINNED_BOB = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
            .withTele(VALID_TELE_BOB).withModTuts(VALID_MODTUT_BOB).withPin(true).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons in sorted order.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons in unsorted order.
     */
    public static AddressBook getUnsortedTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getUnsortedTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getUnsortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }
}
