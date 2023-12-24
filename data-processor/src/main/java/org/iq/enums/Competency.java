package org.iq.enums;

import lombok.extern.java.Log;

@Log
public enum Competency {
    Junior,
    Middle,
    Senior;

    public static Competency fromString(String value) {
        for (Competency val : values()) {
            if (val.name().equalsIgnoreCase(value)) {
                return val;
            }
        }
        log.severe("Unable to parse competency from string: " + value + " default Junior competency will be used.");
        return Competency.Junior;
    }
}
