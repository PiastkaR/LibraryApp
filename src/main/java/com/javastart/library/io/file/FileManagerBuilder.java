package com.javastart.library.io.file;

import com.javastart.library.exception.NoSuchFileTypeException;
import com.javastart.library.io.ConsolePrinter;
import com.javastart.library.io.DataReader;

public class FileManagerBuilder {
    private ConsolePrinter consolePrinter;
    private DataReader dataReader;

    public FileManagerBuilder(ConsolePrinter consolePrinter, DataReader dataReader) {
        this.consolePrinter = consolePrinter;
        this.dataReader = dataReader;
    }

    public FileManager build() {
        consolePrinter.printLine("Choose data format: ");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            default:
                throw new NoSuchFileTypeException("Unhandled file type!");
        }

    }

    private FileType getFileType() {
        boolean typeOK = false;
        FileType result = null;
        do {
            printTypes();
            String type = dataReader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOK = true;
            } catch (IllegalArgumentException exception) {
                consolePrinter.printLine("Unhandled data type! Choose another one.");
            }
        } while (!typeOK);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            consolePrinter.printLine(value.name());
        }

    }
}
