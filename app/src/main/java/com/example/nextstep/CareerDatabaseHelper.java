package com.example.nextstep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CareerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "career.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TIPS = "career_tips";
    public static final String TABLE_QUOTES = "career_quotes";

    public CareerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTips = "CREATE TABLE " + TABLE_TIPS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "career TEXT," +
                "tip TEXT)";

        String createQuotes = "CREATE TABLE " + TABLE_QUOTES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "career TEXT," +
                "quote TEXT)";

        db.execSQL(createTips);
        db.execSQL(createQuotes);

        db.execSQL("CREATE TABLE IF NOT EXISTS quotes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "career TEXT, " +
                "quote TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        onCreate(db);
    }
}
