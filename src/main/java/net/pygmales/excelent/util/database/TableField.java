package net.pygmales.excelent.util.database;

public enum TableField {
    FIRM_NAME("firm_name", String.class),
    TRACK_NUM("track_num", String.class),
    DRIVER_TRACK_NUM("driver_track_num", String.class),
    MSG_RECEIVED_DATE("msg_received_date", String.class),
    MSG_ANSWER_TIME_DAYS("msg_answer_time_days", Integer.class),
    MSG_ANSWER_DATE_MAX("msg_answer_date_max", String.class),
    PHONE_NUMBER("phone_number", String.class);

    private final String fieldName;
    private final Class<?> valueClass;

    TableField(String name, Class<?> valueClass) {
        this.fieldName = name;
        this.valueClass = valueClass;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Class<?> getValueClass() {
        return this.valueClass;
    }

}
