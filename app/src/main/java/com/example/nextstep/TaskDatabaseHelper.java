package com.example.nextstep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nextstep.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COMPLETED = "completed";
    private static final String COLUMN_CAREER = "career";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_COMPLETED + " INTEGER,"
                + COLUMN_CAREER + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void insertTask(Task task, String career) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_COMPLETED, task.isCompleted() ? 1 : 0);
        values.put(COLUMN_CAREER, career);
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public List<Task> getTasksForCareer(String career) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, null, COLUMN_CAREER + "=?",
                new String[]{career}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED)) == 1;
                taskList.add(new Task(id, description, completed));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    public void updateTaskCompletion(int taskId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);
        db.update(TABLE_TASKS, values, COLUMN_ID + "=?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    public int getCompletedTaskCount(String career) {
        return countTasks(career, true);
    }

    public int getTotalTaskCount(String career) {
        return countTasks(career, null);
    }

    private int countTasks(String career, Boolean completedFilter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_TASKS + " WHERE " + COLUMN_CAREER + "=?";
        String[] args;

        if (completedFilter != null) {
            query += " AND " + COLUMN_COMPLETED + "=?";
            args = new String[]{career, completedFilter ? "1" : "0"};
        } else {
            args = new String[]{career};
        }

        Cursor cursor = db.rawQuery(query, args);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }
}
