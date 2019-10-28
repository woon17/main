package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.ReferenceId;
import seedu.address.model.common.Identical;

/**
 * Represents a consultation room involving a single doctor and an optional patient.
 * Guarantees: Reference Id to a doctor is immutable and validated.
 */
public class Room implements Identical<Room> {
    private final ReferenceId doctor;
    private Optional<ReferenceId> patientCurrentlyBeingServed;

    public Room(ReferenceId doctor, Optional<ReferenceId> patient, boolean isResting) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
    }

    public Room(ReferenceId doctor, Optional<ReferenceId> patient) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
    }

    public Room(ReferenceId doctor) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = Optional.empty();
    }

    public boolean isReadyToServe() {
        return patientCurrentlyBeingServed.isEmpty();
    }

    public ReferenceId getDoctor() {
        return doctor;
    }

    public Optional<ReferenceId> getCurrentPatient() {
        return patientCurrentlyBeingServed;
    }

    /**
     * Returns true if both rooms are occupied by the same staff.
     * This defines a weaker notion of equality between two consultation rooms.
     */
    public boolean isSameAs(Room other) {
        requireNonNull(other);
        return other == this // short circuit if same object
                || doctor.equals(other.doctor);
    }

    @Override
    public int compareTo(Room room) {
        return room.getDoctor().compareTo(getDoctor());
    }

    /**
     * Returns true if both rooms occupied by the same staff and patient.
     * This defines a stronger notion of equality between two consultation rooms.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Room)) {
            return false;
        }

        Room o = (Room) other;
        return getDoctor().equals(o.getDoctor())
                && getCurrentPatient().isPresent() == o.getCurrentPatient().isPresent()
                && getCurrentPatient().equals(o.getCurrentPatient());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(doctor);
        return builder.toString();
    }
}
