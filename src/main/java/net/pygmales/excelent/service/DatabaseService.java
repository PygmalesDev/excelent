package net.pygmales.excelent.service;

import net.pygmales.excelent.Main;
import net.pygmales.excelent.record.Notepad;
import net.pygmales.excelent.record.Sending;
import net.pygmales.excelent.util.database.TableFields;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.pygmales.excelent.common.Constants.DB_PATH;
import static net.pygmales.excelent.util.database.TableFields.*;

public class DatabaseService {
    private static final DatabaseService INSTANCE = new DatabaseService();
    private static final String URL = String.format("jdbc:sqlite:%s", DB_PATH);
    private static final Storage STORAGE = Storage.getInstance();

    private Connection connection;
    private String notepadID;
    private String notepadName;
    private final Logger log = Main.getLogger();

    public static DatabaseService getInstance() {
        return INSTANCE;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection(URL);
            if (Objects.nonNull(connection))
                this.log.info("Database driver connection {}", connection.getMetaData().getDriverName());
            this.log.info("Connection to SQLite has been established");
        } catch (SQLException e) {
            this.log.fatal(e.getMessage());
        }
    }

    public void linkWithNotepad(Notepad notepad) {
        this.notepadName = notepad.name();
        this.notepadID = notepad.tableID();
        this.createTableIfNotExists();
        this.loadTableEntries();
    }

    public void createTableIfNotExists() {
        final String sql = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id                       INTEGER     PRIMARY KEY,
                    company_name             TEXT        NOT NULL,
                    track_num                TEXT,
                    driver_track_num         TEXT,
                    msg_received_date        TEXT,
                    msg_answer_time_days     INTEGER,
                    msg_answer_date_max      TEXT,
                    phone_number             TEXT,
                    open_status              INTEGER
                );
                """, this.notepadID);
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(sql);
            this.log.info("Table '{}' for file '{}' has been opened", this.notepadID, this.notepadName);
        } catch (SQLException e) {
            this.log.fatal(e.getMessage());
        }
    }

    public boolean createSendingEntry(Sending sending) {
        if (sending.companyName().isEmpty()) {
            this.log.error("Sending for table {} does not contain non-null field {}!", this.notepadName, COMPANY_NAME);
            return false;
        }

        StringBuilder values = new StringBuilder();
        values.repeat("?, ", TableFields.getAll().size());
        values.delete(values.length()-2, values.length());

        final String sql = String.format("""
                INSERT INTO %s(%s)
                    VALUES(%s);
                """, this.notepadID, TableFields.getFieldsAsString(), values);

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, sending.companyName());
            statement.setString(2, sending.trackNumber());
            statement.setString(3, sending.driverTrackNumber());
            statement.setString(4, sending.messageReceivedDate().toString());
            statement.setInt(5, sending.messageAnswerTimeDays());
            statement.setString(6, sending.messageAnswerDaysMax().toString());
            statement.setString(7, sending.phoneNumber());
            statement.setInt(8, sending.openStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            this.log.fatal(e.getMessage());
        }

        STORAGE.putSending(sending);
        return true;
    }

    private void loadTableEntries() {
        final String sql = String.format("""
                SELECT * from %s;
                """, this.notepadID);
        List<Sending> sendingList = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                sendingList.add(new Sending(
                        result.getString(COMPANY_NAME),
                        result.getString(TRACK_NUM),
                        result.getString(DRIVER_TRACK_NUM),
                        LocalDate.parse(result.getString(MSG_RECEIVED_DATE)),
                        result.getInt(MSG_ANSWER_TIME_DAYS),
                        LocalDate.parse(result.getString(MSG_ANSWER_DATE_MAX)),
                        result.getString(PHONE_NUMBER),
                        result.getInt(OPEN_STATUS)));
            }
        } catch (SQLException e) {
            log.error("Failed to load sending for notepad `{}` from database table `{}`: {}",
                    this.notepadName, this.notepadID, e.getMessage());
        }
        STORAGE.putSendings(sendingList);
    }
}
