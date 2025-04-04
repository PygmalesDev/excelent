package net.pygmales.excelent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.ImageCache;
import net.pygmales.excelent.service.Storage;

import java.util.function.Supplier;

public class App extends Application {
    private static final DatabaseService DATABASE = DatabaseService.getInstance();
    private static final Storage STORAGE = Storage.getInstance();
    private static final Stage STAGE = new Stage();

    @Override
    public void start(Stage stage) {
        STAGE.getIcons().add(ImageCache.get("ss.png"));
        STAGE.setTitle("Excelent!");
        STAGE.setResizable(true);

        FileManager.getLastEditedNotepad().ifPresentOrElse(
                notepad -> setScene(Scenes.FILE_HANDLER),
                () -> setScene(Scenes.MAIN_MENU));

        STAGE.show();
    }

    public static Stage getStage() {
        return STAGE;
    }

    public static void setScene(Supplier<Scene> scene) {
        STAGE.setScene(scene.get());
    }
}
