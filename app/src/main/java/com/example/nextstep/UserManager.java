package com.example.nextstep;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_SELECTED_CAREER = "selected_career";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_HAS_COMPLETED_QUIZ = "has_completed_quiz";
    private static final String KEY_PROFILE_PICTURE = "profile_picture";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public void saveSelectedCareer(String career) {
        editor.putString(KEY_SELECTED_CAREER, career);
        editor.putBoolean(KEY_HAS_COMPLETED_QUIZ, true);
        editor.apply();
    }

    public String getSelectedCareer() {
        return preferences.getString(KEY_SELECTED_CAREER, "");
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean hasCompletedQuiz() {
        return preferences.getBoolean(KEY_HAS_COMPLETED_QUIZ, false);
    }

    public void saveProfilePicture(String encodedImage) {
        editor.putString(KEY_PROFILE_PICTURE, encodedImage);
        editor.apply();
    }

    public String getProfilePicture() {
        return preferences.getString(KEY_PROFILE_PICTURE, null);
    }

    public void logout() {
        editor.clear();
        editor.apply();

        // Clear all user data in database
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(context);
        dbHelper.clearUserData();
    }

    public boolean hasCompletedOnboarding() {
        return isLoggedIn() && !getSelectedCareer().isEmpty();
    }
}
