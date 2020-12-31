package com.javastart.library.io.file;

import com.javastart.library.exception.DataExportException;
import com.javastart.library.exception.DataImportException;
import com.javastart.library.exception.InvalidDataException;
import com.javastart.library.model.Book;
import com.javastart.library.model.Library;
import com.javastart.library.model.Magazine;
import com.javastart.library.model.Publication;

import java.io.*;
import java.util.Scanner;

public class CsvFileManager implements FileManager {
    private static final String FILE_NAME = "Library.csv";

    @Override
    public Library importData() {
        Library library = new Library();
        try (var fileReader = new Scanner(new File(FILE_NAME))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException exception) {
            throw new DataImportException("Error while importing a file: " + FILE_NAME);
        }
        return library;
    }

    private Publication createObjectFromString(String csvLine) {
        String[] split = csvLine.split(";");
        String type = split[0];
        if (Book.TYPE.equals(type)) {
            return createBook(split);
        } else if (Magazine.TYPE.equals(type)) {
            return createMagazine(split);
        }
        throw new InvalidDataException("Unknown publication type: " + type);
    }

    private Publication createMagazine(String[] data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.parseInt(data[3]);
        int month = Integer.parseInt(data[4]);
        int day = Integer.parseInt(data[5]);
        String language = data[6];

        return new Magazine(title, publisher, language, year,month, day);
    }

    private Publication createBook(String[] data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.parseInt(data[3]);
        String author = data[4];
        int pages = Integer.parseInt(data[5]);
        String isb = data[6];
        return new Book(title, author, year, pages, publisher, isb);
    }

    @Override
    public void exportData(Library library) {
        Publication[] publications = library.getPublications();
        try (
                var fileWriter = new FileWriter(FILE_NAME);
                var bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (Publication publication : publications) {
                bufferedWriter.write(publication.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException exception) {
            throw new DataExportException("Error while exporting a file: " + FILE_NAME);
        }
    }
}
