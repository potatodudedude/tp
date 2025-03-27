package seedu.address.model.person.exceptions;

/**
 * Signals that operation requires that two Persons must have the same identity
 * except for their pin status.
 */
public class PersonMustBeSameException extends RuntimeException {}
