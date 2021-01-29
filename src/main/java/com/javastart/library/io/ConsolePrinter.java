package com.javastart.library.io;

import com.javastart.library.model.*;

import java.util.Collection;

public class ConsolePrinter {
    public void printBooks(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();

        if (count == 0)
            printLine("No books in library.");
    }

    public void printMagazines(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(publication -> publication instanceof Magazine)
                .map(Publication::toString)
                .count();

        if (count == 0)
            printLine("No magazines in library.");
    }

    public void printLine(String text) {
        System.out.println(text);
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }
}
