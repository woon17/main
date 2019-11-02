package seedu.address.model.events;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;

/**
 * new Appoint with timing and status and personId
 * no need to relate to personName, instead of playing with personId
 */
public class DutyEvent extends Event {
    /**
     * Every field must be present and not null.
     *
     * @param personId
     * @param timing
     * @param status
     */
    public DutyEvent(ReferenceId personId, Timing timing, Status status) {
        super(personId, timing, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Duty shift - Doctor ID: ")
                .append(getPersonId())
                .append(" Timing: ")
                .append(getEventTiming());
        return builder.toString();
    }
}