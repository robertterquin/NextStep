package com.example.nextstep;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalManager {
    private static final String TAG = "GoalManager";
    private static final String PREFS_NAME = "GoalPreferences";
    private static final String KEY_GOALS = "goals";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public GoalManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }

    public void saveGoal(Goal goal) {
        List<Goal> goals = getAllGoals();
        
        // Check if the goal already exists (for updating)
        boolean exists = false;
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i).getId().equals(goal.getId())) {
                goals.set(i, goal);
                exists = true;
                break;
            }
        }
        
        // If it doesn't exist, add it
        if (!exists) {
            goals.add(goal);
        }
        
        saveAllGoals(goals);
        Log.d(TAG, "saveGoal: Saved goal " + goal.getTitle() + ", ID: " + goal.getId());
    }

    public void saveAllGoals(List<Goal> goals) {
        String goalsJson = gson.toJson(goals);
        editor.putString(KEY_GOALS, goalsJson);
        editor.apply();
        Log.d(TAG, "saveAllGoals: Saved " + goals.size() + " goals");
    }

    public List<Goal> getAllGoals() {
        String goalsJson = preferences.getString(KEY_GOALS, "");
        
        if (goalsJson.isEmpty()) {
            return new ArrayList<>();
        }
        
        Type type = new TypeToken<List<Goal>>(){}.getType();
        
        try {
            List<Goal> goals = gson.fromJson(goalsJson, type);
            return goals != null ? goals : new ArrayList<>();
        } catch (Exception e) {
            Log.e(TAG, "getAllGoals: Error parsing goals", e);
            return new ArrayList<>();
        }
    }
    
    public List<Goal> getActiveGoals() {
        List<Goal> allGoals = getAllGoals();
        List<Goal> activeGoals = new ArrayList<>();
        
        for (Goal goal : allGoals) {
            if (!goal.isCompleted()) {
                activeGoals.add(goal);
            }
        }
        
        return activeGoals;
    }
    
    public List<Goal> getCompletedGoals() {
        List<Goal> allGoals = getAllGoals();
        List<Goal> completedGoals = new ArrayList<>();
        
        for (Goal goal : allGoals) {
            if (goal.isCompleted()) {
                completedGoals.add(goal);
            }
        }
        
        return completedGoals;
    }
    
    public List<Goal> getGoalsDueToday() {
        List<Goal> allGoals = getAllGoals();
        List<Goal> todayGoals = new ArrayList<>();
        
        for (Goal goal : allGoals) {
            if (goal.isDueToday()) {
                todayGoals.add(goal);
            }
        }
        
        return todayGoals;
    }
    
    public int getActiveTodayGoalsCount() {
        return getGoalsDueToday().size();
    }

    public boolean deleteGoal(String goalId) {
        List<Goal> goals = getAllGoals();
        boolean removed = false;
        
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i).getId().equals(goalId)) {
                goals.remove(i);
                removed = true;
                break;
            }
        }
        
        if (removed) {
            saveAllGoals(goals);
            Log.d(TAG, "deleteGoal: Deleted goal with ID: " + goalId);
        }
        
        return removed;
    }
    
    public static String formatDate(Date date) {
        if (date == null) return "Not set";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
