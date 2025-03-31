package net.pygmales.excelent.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import net.pygmales.excelent.App;

import java.io.IOException;
import java.util.function.Supplier;

public class Scenes {
    public static final Supplier<Scene> OPENING = registerSupplier("main-menu");
    public static final Supplier<Scene> FILE_HANDLER = registerSupplier("file-handler");
    public static final Supplier<Scene> CALENDAR_ENTRY = registerSupplier("calendar-entry");

    private static Supplier<Scene> registerSupplier(String name) {
        return () -> {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scene/" + name + ".fxml"));
            try {
                return new Scene(fxmlLoader.load(), 800, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void load() {}
}
