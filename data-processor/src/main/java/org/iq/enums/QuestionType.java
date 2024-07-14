package org.iq.enums;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum QuestionType {
    TEXT,
    CODE,
    SINGLE,
    MULTI;

    public static QuestionType fromString(String value) {
        for (QuestionType val : values()) {
            if (val.name().equalsIgnoreCase(value)) {
                return val;
            }
        }
        log.warn("Unable to parse competency from string: '{}'. Default TEXT type will be used.", value);
        return QuestionType.TEXT;
    }
}
