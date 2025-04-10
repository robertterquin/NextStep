package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home_page extends AppCompatActivity {
    private UserManager userManager;
    private TaskManager taskManager;
    private GoalManager goalManager;
    private CareerTipManager careerTipManager;
    
    private TextView tvTasksCompleted, tvGoalsToday, tvOverallProgress, tvCareerTip, tvViewMoreTips;
    private ProgressBar overallProgressBar;
    private CardView cardAssessment, cardAddGoal, cardTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize managers
        userManager = new UserManager(this);
        String selectedCareer = userManager.getSelectedCareer();
        if (selectedCareer.isEmpty()) {
            selectedCareer = UserResponses.DEVELOPER; // Default if none selected
        }
        taskManager = new TaskManager(this, selectedCareer);
        goalManager = new GoalManager(this);
        careerTipManager = new CareerTipManager(this);

        // Initialize views
        TextView greetingText = findViewById(R.id.greeting_text);
        tvTasksCompleted = findViewById(R.id.tvTasksCompleted);
        tvGoalsToday = findViewById(R.id.tvGoalsToday);
        tvOverallProgress = findViewById(R.id.tvOverallProgress);
        overallProgressBar = findViewById(R.id.overallProgressBar);
        tvCareerTip = findViewById(R.id.tvCareerTip);
        tvViewMoreTips = findViewById(R.id.tvViewMoreTips);
        
        // Initialize card views
        cardAssessment = findViewById(R.id.cardAssessment);
        cardAddGoal = findViewById(R.id.cardAddGoal);
        cardTodoList = findViewById(R.id.cardTodoList);

        // Set greeting text
        String userName = userManager.getUserName();
        if (!userName.isEmpty()) {
            String greeting = getGreeting() + ", " + userName;
            greetingText.setText(greeting);
        }

        // Set career tip
        updateCareerTip(selectedCareer);
        
        // Set up click listeners
        setupClickListeners();

        // Update statistics
        updateStatistics();

        // Setup bottom navigation using the helper
        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.HOME);
    }
    
    private void setupClickListeners() {
        cardAssessment.setOnClickListener(v -> {
            Intent intent = new Intent(home_page.this, quiz.class);
            startActivity(intent);
        });

        cardAddGoal.setOnClickListener(v -> {
            Intent intent = new Intent(home_page.this, goals_page.class);
            startActivity(intent);
        });
        
        cardTodoList.setOnClickListener(v -> {
            Intent intent = new Intent(home_page.this, task_page.class);
            startActivity(intent);
        });
        
        tvViewMoreTips.setOnClickListener(v -> {
            // Show more tips (could be implemented as a dialog or new activity)
            Toast.makeText(this, "More career tips coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void updateCareerTip(String career) {
        String tip = careerTipManager.getRandomTipForCareer(career);
        tvCareerTip.setText(tip);
    }
    
    private String getGreeting() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            return "Good Morning";
        } else if (hour < 18) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }
    
    private void updateStatistics() {
        // Update Tasks Completed
        int completedTasks = taskManager.getCompletedTaskCount();
        int totalTasks = taskManager.getTotalTaskCount();
        tvTasksCompleted.setText(completedTasks + "/" + totalTasks);
        
        // Update Goals Due Today
        int todayGoals = goalManager.getActiveTodayGoalsCount();
        tvGoalsToday.setText(String.valueOf(todayGoals));
        
        // Calculate overall progress (tasks + goals)
        int taskProgress = totalTasks > 0 ? (completedTasks * 100) / totalTasks : 0;
        int completedGoals = goalManager.getCompletedGoals().size();
        int totalGoals = goalManager.getAllGoals().size();
        int goalProgress = totalGoals > 0 ? (completedGoals * 100) / totalGoals : 0;
        
        // Combined progress (weighted average)
        int overallProgress;
        if (totalTasks > 0 && totalGoals > 0) {
            overallProgress = (taskProgress + goalProgress) / 2;
        } else if (totalTasks > 0) {
            overallProgress = taskProgress;
        } else if (totalGoals > 0) {
            overallProgress = goalProgress;
        } else {
            overallProgress = 0;
        }
        
        // Update progress bar and text
        overallProgressBar.setProgress(overallProgress);
        tvOverallProgress.setText(overallProgress + "% Complete");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh statistics when returning to the screen
        updateStatistics();
    }
}
