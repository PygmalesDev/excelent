package net.pygmales.excelent.service;

import javafx.scene.image.Image;
import net.pygmales.excelent.App;
import net.pygmales.excelent.Main;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageCache {
    private static final Logger logger = Main.getLogger();
    private static final Map<String, Image> IMAGES = new HashMap<>();

    private ImageCache() {}

    public static Image get(String path) {
        path = String.join("/", "image", path);
        return IMAGES.computeIfAbsent(path, ImageCache::loadImage);
    }

    private static Image loadImage(String path) {
        URL url = Main.class.getResource(path);
        if (Objects.isNull(url)) {
            logger.warn("Failed not locate image '{}' in the project resources", path);

            URL blankImgPath = Main.class.getResource("image/texture.png");
            if (Objects.nonNull(blankImgPath)) return new Image(blankImgPath.toExternalForm());

            logger.error("Temp image not found in the project resources!");
            return null;
        }

        final String finalPath = url.toExternalForm();
        Image img = new Image(finalPath);
        img.errorProperty().addListener((obs, oldv, newv) -> {
            if (newv) logger.warn("Failed to load image `{}`", finalPath);
        });

        return img;
    }

    public static void load() {}

}
