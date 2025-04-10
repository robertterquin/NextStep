package com.example.nextstep;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Goal {
    private static final String TAG = "Goal";
    
    private String id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean completed;

    // Default constructor needed for GSON
    public Goal() {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        Log.d(TAG, "Goal: Created with default constructor, ID: " + id);
    }

    public Goal(String title, String description, Date startDate, Date endDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = false;
        Log.d(TAG, "Goal: Created goal '" + title + "', ID: " + id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isActive() {
        try {
            Date now = new Date();
            return !completed && startDate != null && endDate != null && 
                   now.after(startDate) && now.before(endDate);
        } catch (Exception e) {
            Log.e(TAG, "isActive: Error checking if goal is active", e);
            return false;
        }
    }

    public boolean isDueToday() {
        try {
            // Check if the goal is due today (falls between start and end dates)
            Date now = new Date();
            
            if (startDate == null || endDate == null) {
                return false;
            }
            
            // Create calendar instances for comparison
            Calendar goalStartCal = Calendar.getInstance();
            Calendar goalEndCal = Calendar.getInstance();
            Calendar nowCal = Calendar.getInstance();
            
            // Set dates
            goalStartCal.setTime(startDate);
            goalEndCal.setTime(endDate);
            nowCal.setTime(now);
            
            // Reset time to start of day for clean comparison
            resetTimeToStartOfDay(goalStartCal);
            resetTimeToStartOfDay(goalEndCal);
            resetTimeToStartOfDay(nowCal);
            
            // Check if today is between start and end dates (inclusive)
            boolean afterOrEqualToStart = nowCal.compareTo(goalStartCal) >= 0;
            boolean beforeOrEqualToEnd = nowCal.compareTo(goalEndCal) <= 0;
            
            boolean isDueToday = !completed && afterOrEqualToStart && beforeOrEqualToEnd;
            
            if (isDueToday) {
                Log.d(TAG, "isDueToday: Goal '" + title + "' is due today");
            }
            
            return isDueToday;
        } catch (Exception e) {
            Log.e(TAG, "isDueToday: Error checking if goal is due today", e);
            return false;
        }
    }
    
    private void resetTimeToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
