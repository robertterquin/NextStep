package com.example.nextstep;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalManager {
    private static final String TAG = "GoalManager";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private GoalsDatabaseHelper dbHelper;

    public GoalManager(Context context) {
        dbHelper = new GoalsDatabaseHelper(context);
    }

    public void saveGoal(Goal goal) {
        dbHelper.saveGoal(goal);
    }

    public void saveALLGoals(List<Goal> goals) {
        dbHelper.saveALLGoals(goals);
    }

    public List<Goal> getAllGoals() {
        return dbHelper.getAllGoals();
    }

    public boolean deleteGoal(String goalId) {
        return dbHelper.deleteGoal(goalId);
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

    public int getActiveTodayGoalsCount() {
        List<Goal> activeGoals = getActiveGoals();
        int count = 0;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        for (Goal goal : activeGoals) {
            Date startDate = goal.getStartDate();
            Date endDate = goal.getEndDate();

            if (startDate != null && endDate != null) {
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(startDate);
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);
                startCal.set(Calendar.MILLISECOND, 0);

                Calendar endCal = Calendar.getInstance();
                endCal.setTime(endDate);
                endCal.set(Calendar.HOUR_OF_DAY, 0);
                endCal.set(Calendar.MINUTE, 0);
                endCal.set(Calendar.SECOND, 0);
                endCal.set(Calendar.MILLISECOND, 0);

                if (!today.before(startCal) && !today.after(endCal)) {
                    count++;
                }
            }
        }

        return count;
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }

    public static Date parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
