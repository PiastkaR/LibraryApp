package com.javastart.library.io.file;

import com.javastart.library.model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}
