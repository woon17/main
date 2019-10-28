package seedu.address.commons.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for OmniPanel's Tab.
 */
public enum OmniPanelTab {

    PATIENTS_TAB("patientsTab"),
    APPOINTMENTS_TAB("appointmentsTab"),
    DOCTORS_TAB("doctorsTab"),
    DUTYSHIFT_TAB("dutyShiftTab");

    private static final Map<String, OmniPanelTab> BY_ID = new HashMap<>();

    private final String id;

    static {
        for (OmniPanelTab omniPanelTab: values()) {
            BY_ID.put(omniPanelTab.id, omniPanelTab);
        }
    }

    OmniPanelTab(String id) {
        this.id = id;
    }

    public static OmniPanelTab valueOfId(String id) {
        return BY_ID.get(id);
    }
}
