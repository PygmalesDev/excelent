package net.pygmales.excelent.service;

import net.pygmales.excelent.Main;
import net.pygmales.excelent.util.database.TableEntry;
import net.pygmales.excelent.util.database.TableField;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Objects;

import static net.pygmales.excelent.util.database.TableField.FIRM_NAME;

public class DatabaseService {
    private static final DatabaseService INSTANCE = new DatabaseService();
    private static final String URL = "jdbc:sqlite:data/excelent.db";
    private static final Storage STORAGE = Storage.getInstance();

    private Connection connection;
    private int fileTableIndex;
    private String fileName;
    private final Logger log = Main.getLogger();

    public static DatabaseService getInstance() {
        return INSTANCE;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL);
            if (Objects.nonNull(connection)) {
                DatabaseMetaData meta = connection.getMetaData();
                Main.getLogger().info("Database driver connection {}", meta.getDriverName());
                Main.getLogger().info("New database has been created");
            }

            Main.getLogger().info("Connection to SQLite has been established");
        } catch (SQLException e) {
            Main.getLogger().fatal(e.getMessage());
        }
    }

    public void openTable(String fileTableName) {
        this.fileTableIndex = fileTableName.hashCode();
        this.fileName = STORAGE.getOpenedFile().getName();
    }

    public void createTable() {
        final String request = String.format("""
                CREATE TABLE IF NOT EXISTS %d (
                    id                       INTEGER     PRIMARY KEY,
                    firm_name                TEXT        NOT NULL,
                    track_num                TEXT,
                    driver_track_num         TEXT,
                    msg_received_date        TEXT,
                    msg_answer_time_days     INTEGER,
                    msg_answer_date_max      TEXT,
                    phone_number             TEXT
                );
                """, this.fileTableIndex);
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(request);
            Main.getLogger().info("New table {} for file {} has been created",
                    this.fileTableIndex, STORAGE.getOpenedFile().getName());
        } catch (SQLException e) {
            Main.getLogger().fatal(e.getMessage());
        }
    }

    public void insertTransaction(List<TableEntry<?>> entries) {
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();

        if (entries.stream().map(TableEntry::field).noneMatch(entry -> Objects.equals(entry, FIRM_NAME))) {
            log.warn("New transaction for table {} does not contain non-null field {}!",
                    this.fileName, FIRM_NAME.getFieldName());
            return;
        }

        entries.forEach(entry -> {
            keys.append(String.format("%s, ", entry.field().name()));
            values.append("?, ");
        });
        keys.delete(keys.length()-2, keys.length());
        values.delete(values.length()-2, keys.length());

        final String request = String.format("""
                INSERT INTO %s(%s)
                    VALUES(%s);
                """, this.fileTableIndex, keys, values);

        try {
            PreparedStatement statement = this.connection.prepareStatement(request);
            for (int i = 0; i < entries.size(); i++) {
                TableEntry<?> entry = entries.get(i);
                if (entry.field().getValueClass().isInstance(entry.value())) {
                    if (entry.value() instanceof String str) statement.setString(i, str);
                    if (entry.value() instanceof Integer ig) statement.setInt(i, ig);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            Main.getLogger().fatal(e.getMessage());
        }
    }
}
