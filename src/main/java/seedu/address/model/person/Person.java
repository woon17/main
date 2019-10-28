package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.common.Identical;
import seedu.address.model.ReferenceId;
import seedu.address.model.common.Tag;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;


/**
 * Represents a Person who can be either a patient or staff doctor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Identical<Person> {

    // Identity fields
    private final ReferenceId referenceId;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(ReferenceId referenceId, Name name, Phone phone, Email email,
                  Address address, Set<Tag> tags) {
        requireAllNonNull(referenceId, name);
        this.referenceId = referenceId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public ReferenceId getReferenceId() {
        return referenceId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same reference id and name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAs(ReferenceId id) {
        return getReferenceId().equals(id);
    }

    /**
     * Returns true if both persons of the same reference id and name.
     * This defines a weaker notion of equality between two persons.
     */
    @Override
    public boolean isSameAs(Person otherPerson) {
        return otherPerson == this || (otherPerson != null && isSameAs(otherPerson.getReferenceId()));
    }

    @Override
    public int compareTo(Person o) {
        requireNonNull(o);
        return getReferenceId().compareTo(o.getReferenceId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getReferenceId().equals(getReferenceId())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(referenceId, name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getReferenceId())
                .append(" Name: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
