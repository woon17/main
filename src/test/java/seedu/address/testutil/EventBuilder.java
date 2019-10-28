package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;
import seedu.address.model.person.parameters.PatientReferenceId;

/**
 * A utility class to help with building Person objects.
 */
public class EventBuilder {

    public static final String DEFAULT_REFERENCE_ID = "1234567A";
    public static final DateTime DEFAULT_DATETIME = DateTime.tryParseSimpleDateFormat("11/11/19 1800");

    private ReferenceId id;
    private Timing timing;
    private Status status;

    public EventBuilder(int afterYears, int afterMonth, int afterDays,
                        int afterHours, int afterMinutes) {
        id = new PatientReferenceId(DEFAULT_REFERENCE_ID);
        withStartTime(afterYears, afterMonth, afterDays, afterHours, afterMinutes, 30);
        status = new Status("APPROVED");
    }

    public EventBuilder() {
        this.id = new PatientReferenceId(DEFAULT_REFERENCE_ID);
        this.timing = new Timing(DEFAULT_DATETIME);
        this.status = new Status("APPROVED");
    }

    /**
     * Initializes the PersonBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        id = eventToCopy.getPersonId();
        timing = eventToCopy.getEventTiming();
        status = eventToCopy.getStatus();
    }

    /**
     * Sets the {@code PatientReferenceId} of the {@code Person} that we are building.
     */
    public EventBuilder withId(String id) {
        this.id = new PatientReferenceId(id);
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(int afterYears, int afterMonth, int afterDays,
                                      int afterHours, int afterMinutes, int durationInMinutes) {

        LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());

        LocalDateTime newStartLocalDateTime = currentLocalDateTime
                .plusYears(afterYears)
                .plusMonths(afterMonth)
                .plusDays(afterDays)
                .plusHours(afterHours)
                .plusMinutes(afterMinutes);

        LocalDateTime newEndLocalDateTime = newStartLocalDateTime.plusMinutes(durationInMinutes);

        Date startDate = Date.from(newStartLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(newEndLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.timing = new Timing(new DateTime(startDate), new DateTime(endDate));
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        DateTime dateTime = DateTime.tryParseSimpleDateFormat(startTime);
        this.timing = new Timing(dateTime);
        return this;
    }

    public EventBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Event build() {
        return new Event(id, timing, status);
    }

}
