package com.example.nextstep;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

/**
 * Helper class to standardize bottom navigation across all activities
 */
public class NavigationHelper {

    public enum NavigationTab {
        HOME, TASKS, GOALS, PROFILE
    }

    /**
     * Sets up the bottom navigation bar for the given activity
     *
     * @param activity The activity where navigation is being set up
     * @param currentTab The currently active tab
     */
    public static void setupBottomNavigation(Activity activity, NavigationTab currentTab) {
        // Find all navigation views
        ImageView navHome = activity.findViewById(R.id.nav_home);
        ImageView navTasks = activity.findViewById(R.id.nav_tasks);
        ImageView navGoals = activity.findViewById(R.id.nav_goals);
        ImageView navProfile = activity.findViewById(R.id.nav_profile);

        View strikeHome = activity.findViewById(R.id.strike_home);
        View strikeTasks = activity.findViewById(R.id.strike_tasks);
        View strikeGoals = activity.findViewById(R.id.strike_goals);
        View strikeProfile = activity.findViewById(R.id.strike_profile);

        // Set all indicators to invisible initially
        strikeHome.setVisibility(View.INVISIBLE);
        strikeTasks.setVisibility(View.INVISIBLE);
        strikeGoals.setVisibility(View.INVISIBLE);
        strikeProfile.setVisibility(View.INVISIBLE);

        // Set the current tab indicator to visible
        switch (currentTab) {
            case HOME:
                strikeHome.setVisibility(View.VISIBLE);
                break;
            case TASKS:
                strikeTasks.setVisibility(View.VISIBLE);
                break;
            case GOALS:
                strikeGoals.setVisibility(View.VISIBLE);
                break;
            case PROFILE:
                strikeProfile.setVisibility(View.VISIBLE);
                break;
        }

        // Set click listeners for navigation
        navHome.setOnClickListener(v -> {
            if (currentTab != NavigationTab.HOME) {
                navigateTo(activity, home_page.class);
            }
        });

        navTasks.setOnClickListener(v -> {
            if (currentTab != NavigationTab.TASKS) {
                navigateTo(activity, task_page.class);
            }
        });

        navGoals.setOnClickListener(v -> {
            if (currentTab != NavigationTab.GOALS) {
                navigateTo(activity, goals_page.class);
            }
        });

        navProfile.setOnClickListener(v -> {
            if (currentTab != NavigationTab.PROFILE) {
                navigateTo(activity, profile_page.class);
            }
        });
    }

    /**
     * Navigate to the specified activity with a smooth transition
     */
    private static void navigateTo(Activity currentActivity, Class<?> destinationClass) {
        Intent intent = new Intent(currentActivity, destinationClass);
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(0, 0); // No animation
    }
    
    /**
     * Navigate to login flow (for when user logs out)
     */
    public static void navigateToLoginFlow(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }
}
