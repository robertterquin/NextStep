package com.example.nextstep;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class UserResponses {
    private static final String PREFS_NAME = "QuizResponses";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // Career categories
    public static final String DEVELOPER = "Developer";
    public static final String NETWORK_SPECIALIST = "Network Specialist";
    public static final String IT_SUPPORT = "IT Support & Administration";
    public static final String DATA_ANALYTICS = "Data & Analytics";
    public static final String UI_DESIGNER = "UI Designer";
    public static final String PROJECT_MANAGER = "Project Manager";
    public static final String CYBER_SECURITY = "Cyber Security";

    public UserResponses(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // Save response for a question (true for Yes, false for No)
    public void saveResponse(int questionNumber, boolean isYes) {
        editor.putBoolean("Q" + questionNumber, isYes);
        editor.apply();
    }

    // Get response for a question
    public boolean getResponse(int questionNumber) {
        return preferences.getBoolean("Q" + questionNumber, false);
    }

    // Calculate career scores based on responses
    public Map<String, Integer> calculateCareerScores() {
        Map<String, Integer> scores = new HashMap<>();

        // Initialize all career scores to 0
        scores.put(DEVELOPER, 0);
        scores.put(NETWORK_SPECIALIST, 0);
        scores.put(IT_SUPPORT, 0);
        scores.put(DATA_ANALYTICS, 0);
        scores.put(UI_DESIGNER, 0);
        scores.put(PROJECT_MANAGER, 0);
        scores.put(CYBER_SECURITY, 0);

        // Developer: Q1, Q8, Q10
        if (getResponse(1)) scores.put(DEVELOPER, scores.get(DEVELOPER) + 1);
        if (getResponse(8)) scores.put(DEVELOPER, scores.get(DEVELOPER) + 1);
        if (getResponse(10)) scores.put(DEVELOPER, scores.get(DEVELOPER) + 1);

        // Network Specialist: Q2, Q9
        if (getResponse(2)) scores.put(NETWORK_SPECIALIST, scores.get(NETWORK_SPECIALIST) + 1);
        if (getResponse(9)) scores.put(NETWORK_SPECIALIST, scores.get(NETWORK_SPECIALIST) + 1);

        // IT Support: Q3, Q9
        if (getResponse(3)) scores.put(IT_SUPPORT, scores.get(IT_SUPPORT) + 1);
        if (getResponse(9)) scores.put(IT_SUPPORT, scores.get(IT_SUPPORT) + 1);

        // Data Analytics: Q4, Q14
        if (getResponse(4)) scores.put(DATA_ANALYTICS, scores.get(DATA_ANALYTICS) + 1);
        if (getResponse(14)) scores.put(DATA_ANALYTICS, scores.get(DATA_ANALYTICS) + 1);

        // UI Designer: Q5, Q13
        if (getResponse(5)) scores.put(UI_DESIGNER, scores.get(UI_DESIGNER) + 1);
        if (getResponse(13)) scores.put(UI_DESIGNER, scores.get(UI_DESIGNER) + 1);

        // Project Manager: Q6, Q12
        if (getResponse(6)) scores.put(PROJECT_MANAGER, scores.get(PROJECT_MANAGER) + 1);
        if (getResponse(12)) scores.put(PROJECT_MANAGER, scores.get(PROJECT_MANAGER) + 1);

        // Cyber Security: Q7, Q11, Q15
        if (getResponse(7)) scores.put(CYBER_SECURITY, scores.get(CYBER_SECURITY) + 1);
        if (getResponse(11)) scores.put(CYBER_SECURITY, scores.get(CYBER_SECURITY) + 1);
        if (getResponse(15)) scores.put(CYBER_SECURITY, scores.get(CYBER_SECURITY) + 1);

        return scores;
    }

    // Convert scores to percentages
    public Map<String, Integer> calculatePercentages() {
        Map<String, Integer> scores = calculateCareerScores();
        Map<String, Integer> percentages = new HashMap<>();

        // Calculate total possible scores for each category
        int developerMax = 3;
        int networkSpecialistMax = 2;
        int itSupportMax = 2;
        int dataAnalyticsMax = 2;
        int uiDesignerMax = 2;
        int projectManagerMax = 2;
        int cyberSecurityMax = 3;

        // Convert to percentages
        percentages.put(DEVELOPER, (scores.get(DEVELOPER) * 100) / developerMax);
        percentages.put(NETWORK_SPECIALIST, (scores.get(NETWORK_SPECIALIST) * 100) / networkSpecialistMax);
        percentages.put(IT_SUPPORT, (scores.get(IT_SUPPORT) * 100) / itSupportMax);
        percentages.put(DATA_ANALYTICS, (scores.get(DATA_ANALYTICS) * 100) / dataAnalyticsMax);
        percentages.put(UI_DESIGNER, (scores.get(UI_DESIGNER) * 100) / uiDesignerMax);
        percentages.put(PROJECT_MANAGER, (scores.get(PROJECT_MANAGER) * 100) / projectManagerMax);
        percentages.put(CYBER_SECURITY, (scores.get(CYBER_SECURITY) * 100) / cyberSecurityMax);

        return percentages;
    }

    // Get recommended career (the one with highest percentage)
    public String getRecommendedCareer() {
        Map<String, Integer> percentages = calculatePercentages();
        String recommended = "";
        int highestScore = 0;

        for (Map.Entry<String, Integer> entry : percentages.entrySet()) {
            if (entry.getValue() > highestScore) {
                highestScore = entry.getValue();
                recommended = entry.getKey();
            }
        }

        return recommended;
    }

    // Reset all responses
    public void resetResponses() {
        editor.clear();
        editor.apply();
    }
}