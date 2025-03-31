package net.pygmales.excelent.service;

import java.io.File;

public class Storage {
    private static final Storage INSTANCE = new Storage();
    private File openedFile;

    public static Storage getInstance() {
        return INSTANCE;
    }

    public File getOpenedFile() {
        return openedFile;
    }

    public void setOpenedFile(File openedFile) {
        this.openedFile = openedFile;
    }

    public static void load() {}
}
