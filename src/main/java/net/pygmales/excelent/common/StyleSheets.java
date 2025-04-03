package net.pygmales.excelent.common;

import net.pygmales.excelent.App;
import net.pygmales.excelent.Main;

import java.net.URL;
import java.util.Objects;

public class StyleSheets {
    public static final String CALENDAR = registerStyle("calendar");
    public static final String FILE_HANDLER = registerStyle("file-handler");

    private static String registerStyle(String name) {
        URL path = App.class.getResource("style/" + name + ".css");
        if (Objects.isNull(path)) {
            Main.getLogger().warn("Failed to load style '{}'", name);
            return "";
        }
        Main.getLogger().info("Successfully loaded style '{}'", name);
        return path.toExternalForm();
    }

    public static void load() {}
}
