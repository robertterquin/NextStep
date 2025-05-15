package com.example.nextstep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "goals_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GOALS = "goals";

    // Columns
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_COMPLETED = "completed";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public GoalsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GOALS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_START_DATE + " TEXT,"
                + KEY_END_DATE + " TEXT,"
                + KEY_COMPLETED + " INTEGER"
                + ")";
        db.execSQL(CREATE_GOALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        onCreate(db);
    }

    public void saveGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, goal.getId());
        values.put(KEY_TITLE, goal.getTitle());
        values.put(KEY_DESCRIPTION, goal.getDescription());
        values.put(KEY_START_DATE, goal.getStartDate() != null ? sdf.format(goal.getStartDate()) : null);
        values.put(KEY_END_DATE, goal.getEndDate() != null ? sdf.format(goal.getEndDate()) : null);
        values.put(KEY_COMPLETED, goal.isCompleted() ? 1 : 0);

        // Get the goal without closing db
        Goal existingGoal = getGoalByIdInternal(db, goal.getId());

        if (existingGoal != null) {
            db.update(TABLE_GOALS, values, KEY_ID + "=?", new String[]{goal.getId()});
        } else {
            db.insert(TABLE_GOALS, null, values);
        }

        db.close();
    }

    public void saveALLGoals(List<Goal> goals) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_GOALS, null, null);

            for (Goal goal : goals) {
                ContentValues values = new ContentValues();
                values.put(KEY_ID, goal.getId());
                values.put(KEY_TITLE, goal.getTitle());
                values.put(KEY_DESCRIPTION, goal.getDescription());
                values.put(KEY_START_DATE, goal.getStartDate() != null ? sdf.format(goal.getStartDate()) : null);
                values.put(KEY_END_DATE, goal.getEndDate() != null ? sdf.format(goal.getEndDate()) : null);
                values.put(KEY_COMPLETED, goal.isCompleted() ? 1 : 0);

                db.insert(TABLE_GOALS, null, values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Goal getGoalById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Goal goal = getGoalByIdInternal(db, id);
        db.close();
        return goal;
    }

    // Helper method for internal access without closing DB
    private Goal getGoalByIdInternal(SQLiteDatabase db, String id) {
        Cursor cursor = null;
        Goal goal = null;

        try {
            cursor = db.query(TABLE_GOALS, null, KEY_ID + "=?", new String[]{id}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                goal = cursorToGoal(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return goal;
    }

    public List<Goal> getAllGoals() {
        List<Goal> goals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GOALS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Goal goal = cursorToGoal(cursor);
                goals.add(goal);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return goals;
    }

    public boolean deleteGoal(String goalId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_GOALS, KEY_ID + "=?", new String[]{goalId});
        db.close();
        return rows > 0;
    }

    private Goal cursorToGoal(Cursor cursor) {
        Goal goal = new Goal();

        goal.setId(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
        goal.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
        goal.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));

        String startDateStr = cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_DATE));
        String endDateStr = cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_DATE));

        try {
            goal.setStartDate(startDateStr != null ? sdf.parse(startDateStr) : null);
            goal.setEndDate(endDateStr != null ? sdf.parse(endDateStr) : null);
        } catch (ParseException e) {
            e.printStackTrace();
            goal.setStartDate(null);
            goal.setEndDate(null);
        }

        goal.setCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COMPLETED)) == 1);

        return goal;
    }
}
