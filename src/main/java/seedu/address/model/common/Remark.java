package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark to an event or person.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Remark // instanceof handles nulls
            && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}