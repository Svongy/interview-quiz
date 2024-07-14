package org.iq.enums;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.warn("Unable to parse competency from string: '{}'. Default Junior competency will be used.", value);
        return Competency.Junior;
    }
}
