package com.javastart.library.app;

import com.javastart.library.exception.*;
import com.javastart.library.io.ConsolePrinter;
import com.javastart.library.io.DataReader;
import com.javastart.library.io.file.FileManager;
import com.javastart.library.io.file.FileManagerBuilder;
import com.javastart.library.model.Book;
import com.javastart.library.model.Library;
import com.javastart.library.model.LibraryUser;
import com.javastart.library.model.Magazine;

import java.util.InputMismatchException;

public class LibraryControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Data imported from file.");
        } catch (DataImportException | InvalidDataException exception) {
            printer.printLine(exception.getMessage());
            printer.printLine("Database initiated.");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case ADD_USER:
                    addUser();
                case PRINT_USERS:
                    printUsers();
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("No such an option, choose another one: ");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", give another:");
            } catch (InputMismatchException ignored) {
                printer.printLine("Wrong value give another:");
            }
        }

        return option;
    }

    private void printOptions() {
        printer.printLine("Choose option: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException | PublicationAlreadyExistsException e) {
            printer.printLine("Failed to create book, incorrect data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Limit reached, cannot add another book");
        }
    }

    private void printBooks() {
        printer.printBooks(library.getPublications().values());
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException | PublicationAlreadyExistsException e) {
            printer.printLine("Failed to create magazine, incorrect data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Limit reached, cannot add another magazine");
        }
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Magazine deleted.");
            else
                printer.printLine("No such magazine.");
        } catch (InputMismatchException e) {
            printer.printLine("Incorrect data for magazine.");
        }
    }

    private void printMagazines() {
        printer.printMagazines(library.getPublications().values());
    }

    private void printUsers() {
        printer.printUsers(library.getUsers().values());
    }

//    private Publication[] getSortedPublication() {
//        Publication[] publications = library.getPublications();
//        Arrays.sort(publications, new AlphabeticalTitleComparator());
//        return publications;
//    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Book deleted.");
            else
                printer.printLine("No such a book.");
        } catch (InputMismatchException e) {
            printer.printLine("Incorrect data for book.");
        }
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export of data to file succeeded!");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("End of program!");
    }

    private enum Option {
        EXIT(0, "Exit program"),
        ADD_BOOK(1, "Add book"),
        ADD_MAGAZINE(2, "Add magazine/ newspaper"),
        PRINT_BOOKS(3, "Show available books"),
        PRINT_MAGAZINES(4, "Show available magazines/ newspapers"),
        ADD_USER(7, "Add user"),
        PRINT_USERS(8, "Show user");

        private int value;
        private String description;

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
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No option of ID " + option);
            }
        }
    }
}