package net.pygmales.excelent;

import javafx.application.Application;
import net.pygmales.excelent.scene.Scenes;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.ImageCache;
import net.pygmales.excelent.service.Storage;

public class Main {
    public static void main(String[] args) {
        Storage.load();
        Scenes.load();
        ImageCache.load();
        FileManager.load();

        Application.launch(App.class, args);
    }
}