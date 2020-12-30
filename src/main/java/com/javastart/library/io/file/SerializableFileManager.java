package com.javastart.library.io.file;

import com.javastart.library.exception.DataExportException;
import com.javastart.library.exception.DataImportException;
import com.javastart.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Library.o";

    @Override
    public Library importData() {
        try (
                var fileInputStream = new FileInputStream(FILE_NAME);
                var objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            return (Library) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new DataImportException("Error while importing data for file: " + FILE_NAME);
        }
    }

    @Override
    public void exportData(Library library) {
        try (
                var fileOutPutStream = new FileOutputStream(FILE_NAME);
                var objectOutputStream = new ObjectOutputStream(fileOutPutStream);
        ) {
            objectOutputStream.writeObject(library);
        } catch (IOException exception) {
            throw new DataExportException("Error while exporting data for file: " + FILE_NAME);
        }

    }
}
