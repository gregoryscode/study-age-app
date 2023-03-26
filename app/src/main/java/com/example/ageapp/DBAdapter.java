package com.example.ageapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DBAdapter {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper DBHelper;
    private static SQLiteDatabase db = null;

    public static SQLiteDatabase getConnection(Context context) {
        DBHelper = new DatabaseHelper(context);
        db = DBHelper.getWritableDatabase();
        return db;
    }

    public static void close() {
        db = null;
        DBHelper.close();
    }

    public static ArrayList<String> getUsers(Context context) {

        ArrayList<String> users = new ArrayList<>();
        DBHelper = new DatabaseHelper(context);
        db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM user", null);

        if (c.moveToFirst()){
            do {
                String name = c.getString(c.getColumnIndex("name"));
                String age = c.getString(c.getColumnIndex("age"));
                users.add(name + " (" + age + ")");
            } while(c.moveToNext());
        }

        c.close();
        db.close();

        return users;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createUserTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        private void createUserTable(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS user(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL," +
                    "age TEXT NOT NULL);";
            db.execSQL(sql);
        }
    }
}
