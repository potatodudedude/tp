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
            .withModTut("CS1234-A10").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withTele("@benson")
            .withModTut("CS1234-A10").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").withTele("@carl_kurz")
            .withModTut("CS1234-A10").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withTele("@daniel")
            .withModTut("CS1234-A10").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withTele("@elleMeyer")
            .withModTut("ES2666-Z01").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withTele("@fiona")
            .withModTut("ES2666-Z01").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("anna@example.com").withTele("@george")
            .withModTut("ES2666-Z01").build();

    //        // Manually added
    //        public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
    //                .withEmail("stefan@example.com").withAddress("little india").build();
    //        public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
    //                .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
            .withTele(VALID_TELE_AMY).withModTut(VALID_MODTUT_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
            .withTele(VALID_TELE_BOB).withModTut(VALID_MODTUT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
