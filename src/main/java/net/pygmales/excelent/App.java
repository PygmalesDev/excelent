package net.pygmales.excelent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.ImageCache;
import net.pygmales.excelent.service.Storage;

import java.util.function.Supplier;

public class App extends Application {
    private final Storage storage = Storage.getInstance();
    private static final Stage STAGE = new Stage();

    @Override
    public void start(Stage stage) {
        STAGE.getIcons().add(ImageCache.get("icon.png"));
        STAGE.setTitle("Excelent!");
        STAGE.setResizable(false);

        FileManager.getLatestFile().ifPresentOrElse(file -> {
            this.storage.setOpenedFile(file);
            setScene(Scenes.FILE_HANDLER);
        }, () -> setScene(Scenes.OPENING));

        STAGE.show();
    }

    public static Stage getStage() {
        return STAGE;
    }

    public static void setScene(Supplier<Scene> scene) {
        STAGE.setScene(scene.get());
    }
}
