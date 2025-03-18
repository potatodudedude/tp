package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new TelegramHandle("@alexyeoh"), new Email("alexyeoh@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2100-T01")))),
            new Person(new Name("Bernice Yu"), new TelegramHandle("@berniceyu"), new Email("berniceyu@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2105-T06")))),
            new Person(new Name("Charlotte Oliveiro"), new TelegramHandle("@charly123"),
                    new Email("charlotte@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2103T-T11")))),
            new Person(new Name("David Li"), new TelegramHandle("@daviderr"), new Email("lidavid@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2100-T01")))),
            new Person(new Name("Irfan Ibrahim"), new TelegramHandle("@irfany9"), new Email("irfan@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2106-T03")))),
            new Person(new Name("Roy Balakrishnan"), new TelegramHandle("@royboy8"), new Email("royb@example.com"),
                    new HashSet<>(Collections.singleton(new ModTutGroup("CS2105-T06"))))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a ModTutGroup set containing the list of mod-tut groups given.
     */
    public static Set<ModTutGroup> getModTutSet(String... modTut) {
        return Arrays.stream(modTut)
                .map(ModTutGroup::new)
                .collect(Collectors.toSet());
    }

}
