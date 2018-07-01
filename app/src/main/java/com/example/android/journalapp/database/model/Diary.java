package com.example.android.journalapp.database.model;

public class Diary {
    public static final String TABLE_NAME = "diaries";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESC = "desc";
//    public static final String COLUMN_NOTE = "note";
//    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String desc;
//    private String note;
//    private String image;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DESC + " TEXT,"
//                    + COLUMN_NOTE + " TEXT,"
//                    + COLUMN_IMAGE + " Text,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    public Diary() {
    }

    public static String getColumnDesc() {
        return COLUMN_DESC;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Diary(int id, String desc, String timestamp) {
        this.id = id;
        this.desc = desc;
//        this.note = note;
//        this.image = image;
        this.timestamp = timestamp;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}