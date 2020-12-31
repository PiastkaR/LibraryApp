package com.javastart.library.app;

import com.javastart.library.exception.NoSuchOptionException;

public enum Option {
    EXIT(0, "Exit program"),
    ADD_BOOK(1, "Add book"),
    ADD_MAGAZINE(2,"Add magazine/ newspaper"),
    PRINT_BOOKS(3, "Show available books"),
    PRINT_MAGAZINES(4, "Show available magazines/newspapers");

    private int value;
    private String description;

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    Option(int value, String desc) {
        this.value = value;
        this.description = desc;
    }

    @Override
    public String toString() {
        return value + " - " + description;
    }

    static Option createFromInt(int option) throws NoSuchOptionException {
        try {
            return Option.values()[option];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("No option of id " + option);
        }
    }
}
