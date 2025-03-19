package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEGRAMHANDLE = "@Amy_13ee";
    public static final String DEFAULT_MODTUTGRP = "CS1234-A10";

    private Name name;
    private Email email;
    private TelegramHandle telegramHandle;
    private Set<ModTutGroup> modTutGroup = new HashSet<>();

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        telegramHandle = new TelegramHandle(DEFAULT_TELEGRAMHANDLE);
        modTutGroup.add(new ModTutGroup(DEFAULT_MODTUTGRP));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        email = personToCopy.getEmail();
        telegramHandle = personToCopy.getTelegramHandle();
        modTutGroup = personToCopy.getModTutGroups();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code TelegramHandle} of the {@code Person} that we are building.
     */
    public PersonBuilder withTele(String teleHandle) {
        this.telegramHandle = new TelegramHandle(teleHandle);
        return this;
    }

    /**
     * Parses the {@code modTuts} into a {@code Set<ModTutGroup>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withModTuts(String... modTuts) {
        this.modTutGroup = SampleDataUtil.getModTutSet(modTuts);
        return this;
    }

    /**
     * Builds the {@code Person} object.
     */
    public Person build() {
        return new Person(name, telegramHandle, email, modTutGroup);
    }

}
