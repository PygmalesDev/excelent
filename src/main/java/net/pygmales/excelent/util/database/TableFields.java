package net.pygmales.excelent.util.database;

import java.util.List;

public class TableFields {
    public static final String COMPANY_NAME = "company_name";
    public static final String TRACK_NUM = "track_num";
    public static final String DRIVER_TRACK_NUM = "driver_track_num";
    public static final String MSG_RECEIVED_DATE = "msg_received_date";
    public static final String MSG_ANSWER_TIME_DAYS = "msg_answer_time_days";
    public static final String MSG_ANSWER_DATE_MAX = "msg_answer_date_max";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String OPEN_STATUS = "open_status";

    public static final List<String> FIELDS = List.of(
            COMPANY_NAME,
            TRACK_NUM,
            DRIVER_TRACK_NUM,
            MSG_RECEIVED_DATE,
            MSG_ANSWER_TIME_DAYS,
            MSG_ANSWER_DATE_MAX,
            PHONE_NUMBER,
            OPEN_STATUS
    );

    public static List<String> getAll() {
        return FIELDS;
    }

    public static String getFieldsAsString() {
        StringBuilder keys = new StringBuilder();
        FIELDS.forEach(entry -> keys.append(String.format("%s, ", entry)));
        keys.delete(keys.length()-2, keys.length());
        return keys.toString();
    }

}
