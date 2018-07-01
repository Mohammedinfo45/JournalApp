package com.example.android.journalapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.android.journalapp.database.model.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "diaries_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Diary.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Diary.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertDiary(String desc) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Diary.COLUMN_DESC, desc);
//        values.put(Diary.COLUMN_NOTE, note);
//        values.put(Diary.COLUMN_IMAGE, image);


        // insert row
        long id = db.insert(Diary.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Diary getDiary(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Diary.TABLE_NAME,
                new String[]{Diary.COLUMN_ID,Diary.COLUMN_DESC, Diary.COLUMN_TIMESTAMP},
                Diary.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Diary diary = new Diary(
                cursor.getInt(cursor.getColumnIndex(Diary.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Diary.COLUMN_DESC)),
                cursor.getString(cursor.getColumnIndex(Diary.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return diary;
    }

    public List<Diary> getAllDiaries() {
        List<Diary> diaries = new ArrayList<>();

        // Select All Query

        String selectQuery = "SELECT  * FROM " + Diary.TABLE_NAME + " ORDER BY " +
                Diary.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Diary diary = new Diary();
                diary.setId(cursor.getInt(cursor.getColumnIndex(Diary.COLUMN_ID)));
                diary.setDesc(cursor.getString(cursor.getColumnIndex(Diary.COLUMN_DESC)));
                diary.setTimestamp(cursor.getString(cursor.getColumnIndex(Diary.COLUMN_TIMESTAMP)));

                diaries.add(diary);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return diaries;
    }

    public int getDiaryCount() {
        String countQuery = "SELECT  * FROM " + Diary.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Diary.COLUMN_DESC, diary.getDesc());
        // updating row
        return db.update(Diary.TABLE_NAME, values, Diary.COLUMN_ID + " = ?",
                new String[]{String.valueOf(diary.getId())});
    }

    public void deleteDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Diary.TABLE_NAME, Diary.COLUMN_ID + " = ?",
                new String[]{String.valueOf(diary.getId())});
        db.close();
    }
}
