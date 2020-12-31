package com.javastart.library.io;

import com.javastart.library.model.Book;
import com.javastart.library.model.Magazine;
import com.javastart.library.model.Publication;

public class ConsolePrinter {
    public void printBooks(Publication[] publications) {
        int counter = 0;
        for (Publication publication : publications) {
            if (publication instanceof Book) {
                printLine(publication.toString());
                counter++;
            }
        }
        if (counter == 0)
            printLine("No books in library.");
    }

    public void printMagazines(Publication[] publications) {
        int counter = 0;
        for (Publication publication : publications) {
            if (publication instanceof Magazine) {
                printLine(publication.toString());
                counter++;
            }
        }
        if (counter == 0)
            printLine("No magazines in library.");
    }

    public void printLine(String text) {
        System.out.println(text);
    }
}
