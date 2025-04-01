package net.pygmales.excelent;

import javafx.application.Application;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.common.Styles;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.ImageCache;
import net.pygmales.excelent.service.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        DatabaseService.getInstance().connect();
        Storage.load();
        Scenes.load();
        Styles.load();
        ImageCache.load();
        FileManager.load();
        Application.launch(App.class, args);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}