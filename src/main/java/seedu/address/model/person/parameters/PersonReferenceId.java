package seedu.address.model.person.parameters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.ReferenceId;

/**
 * Represents a Reference ID for Person.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public abstract class PersonReferenceId implements ReferenceId {

    public static final String MESSAGE_CONSTRAINTS =
        "Reference Id should only contain alphanumeric characters and it should be atleast 3 characters long";

    /*
     * The reference ID should only contain alphanumeric characters.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z0-9]{3,}";
    public final String referenceId;
    public final boolean isStaff;

    /**
     * Constructs a {@code PersonReferenceId}.
     *
     * @param referenceId A valid identifier.
     */
    public PersonReferenceId(String referenceId, boolean isStaff) {
        requireNonNull(referenceId);
        checkArgument(isValidId(referenceId), MESSAGE_CONSTRAINTS);
        this.referenceId = referenceId.toUpperCase();
        this.isStaff = isStaff;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean isSameAs(ReferenceId other) {
        return equals(other);
    }

    @Override
    public int compareTo(ReferenceId o) {
        requireNonNull(o);
        return o.toString().compareTo(toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PersonReferenceId // instanceof handles nulls
                && toString().equals(other.toString())); // state check
    }

    @Override
    public String toString() {
        return referenceId;
    }

    @Override
    public int hashCode() {
        return referenceId.hashCode();
    }

    @Override
    public boolean isStaffDoctor() {
        return isStaff;
    }

    @Override
    public boolean isPatient() {
        return !isStaff;
    }
}
