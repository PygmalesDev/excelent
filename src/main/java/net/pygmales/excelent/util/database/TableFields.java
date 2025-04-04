package net.pygmales.excelent.util.database;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableFields {
    public static final String COMPANY_NAME = "company_name";

    public static final String TRACK_NUM = "track_num";
    public static final String DRIVER_TRACK_NUM = "driver_track_num";

    public static final String MSG_SENT_DATE  = "msg_sent_date";
    public static final String MSG_CHECK_DAYS = "msg_check_days";
    public static final String MSG_CHECK_DATE = "msg_check_date";

    public static final String MSG_RECEIVED_DATE = "msg_received_date";
    public static final String MSG_ANSWER_DAYS = "msg_answer_days";
    public static final String MSG_ANSWER_DATE = "msg_answer_date";

    public static final String PHONE_NUMBER = "phone_number";
    public static final String NOTES = "notes";
    public static final String SENDING_STATUS = "sending_status";

    private static final String INT_TYPE = "INTEGER";
    private static final String TEXT_TYPE = "TEXT";

    public static final Map<String, String> FIELD_TYPES = new LinkedHashMap<>();

    private static void loadFieldTypesMap() {
        FIELD_TYPES.put(COMPANY_NAME,       TEXT_TYPE);
        FIELD_TYPES.put(TRACK_NUM,          TEXT_TYPE);
        FIELD_TYPES.put(DRIVER_TRACK_NUM,   TEXT_TYPE);

        FIELD_TYPES.put(MSG_SENT_DATE,      TEXT_TYPE);
        FIELD_TYPES.put(MSG_CHECK_DAYS,     INT_TYPE);
        FIELD_TYPES.put(MSG_CHECK_DATE,     TEXT_TYPE);

        FIELD_TYPES.put(MSG_RECEIVED_DATE,  TEXT_TYPE);
        FIELD_TYPES.put(MSG_ANSWER_DAYS,    INT_TYPE);
        FIELD_TYPES.put(MSG_ANSWER_DATE,    TEXT_TYPE);

        FIELD_TYPES.put(PHONE_NUMBER,       TEXT_TYPE);
        FIELD_TYPES.put(NOTES,              TEXT_TYPE);
        FIELD_TYPES.put(SENDING_STATUS,     TEXT_TYPE);
    }

    public static String getFieldsAsString() {
        if (FIELD_TYPES.isEmpty()) loadFieldTypesMap();

        StringBuilder keys = new StringBuilder();
        FIELD_TYPES.keySet().forEach(field -> keys.append(String.format("%s,", field)));
        keys.delete(keys.length()-1, keys.length());
        return keys.toString();
    }

    public static String getFieldTypesAsString() {
        if (FIELD_TYPES.isEmpty()) loadFieldTypesMap();

        StringBuilder lines = new StringBuilder();
        FIELD_TYPES.forEach((k, v) -> lines.append(String.format("%s %s,", k, v)));
        lines.delete(lines.length()-1, lines.length());
        return lines.toString();
    }
}
