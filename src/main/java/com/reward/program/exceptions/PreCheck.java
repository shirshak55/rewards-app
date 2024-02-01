package com.reward.program.exceptions;

public class PreCheck {
    private PreCheck() {
        throw new IllegalStateException("Utility class, not intended for instantiation");
    }

    public static void checkArgument(boolean expression, RuntimeException exceptionToThrow) {
        if (!expression) {
            throw exceptionToThrow;
        }
    }
}
