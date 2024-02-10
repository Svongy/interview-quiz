package org.iq.enums;

import lombok.extern.java.Log;

@Log
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
        log.severe("Unable to parse question type from string: " + value + " default TEXT type will be used.");
        return QuestionType.TEXT;
    }
}
