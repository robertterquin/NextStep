package com.example.nextstep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_CAREER = "career";
    private static final String KEY_PROFILE_PIC = "profile_pic";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_CAREER + " TEXT, " +
                KEY_PROFILE_PIC + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);

        // Insert default empty user row
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, "");
        values.put(KEY_CAREER, "");
        values.put(KEY_PROFILE_PIC, "");
        db.insert(TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void updateUser(String username, String career, String profilePic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_CAREER, career);
        values.put(KEY_PROFILE_PIC, profilePic);
        db.update(TABLE_USER, values, KEY_ID + "=1", null);
        db.close();
    }

    public void updateProfilePic(String profilePic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PROFILE_PIC, profilePic);
        db.update(TABLE_USER, values, KEY_ID + "=1", null);
        db.close();
    }

    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_USERNAME + " FROM " + TABLE_USER + " WHERE " + KEY_ID + "=1", null);
        String username = "";
        if (cursor.moveToFirst()) {
            username = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return username;
    }

    public String getCareer() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CAREER + " FROM " + TABLE_USER + " WHERE " + KEY_ID + "=1", null);
        String career = "";
        if (cursor.moveToFirst()) {
            career = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return career;
    }

    public String getProfilePic() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_PROFILE_PIC + " FROM " + TABLE_USER + " WHERE " + KEY_ID + "=1", null);
        String pic = "";
        if (cursor.moveToFirst()) {
            pic = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return pic;
    }

    public void clearUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);  // Delete all rows from user table
        // Insert a new empty row so queries don't fail expecting row with id=1
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, "");
        values.put(KEY_CAREER, "");
        values.put(KEY_PROFILE_PIC, "");
        db.insert(TABLE_USER, null, values);
        db.close();
    }
}
