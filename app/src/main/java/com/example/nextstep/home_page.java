package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView tvLearnMoreDescription;
    private Button btnLearnMore;
    private ProgressBar overallProgressBar;
    private CardView cardAssessment, cardAddGoal, cardTodoList;
    private String selectedCareer;

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
        selectedCareer = userManager.getSelectedCareer();
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
        tvLearnMoreDescription = findViewById(R.id.tvLearnMoreDescription);
        btnLearnMore = findViewById(R.id.btnLearnMore);
        

        cardAssessment = findViewById(R.id.cardAssessment);
        cardAddGoal = findViewById(R.id.cardAddGoal);
        cardTodoList = findViewById(R.id.cardTodoList);


        String userName = userManager.getUserName();
        if (!userName.isEmpty()) {
            String greeting = getGreeting() + ", " + userName;
            greetingText.setText(greeting);
        }


        updateCareerTip(selectedCareer);
        

        setupClickListeners();


        updateStatistics();
        

        setupLearnMoreSection();


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
        
        btnLearnMore.setOnClickListener(v -> {
            openCareerResourceUrl();
        });
    }
    
    private void setupLearnMoreSection() {
        // Customize the learn more description based on selected career
        String customMessage = "Explore resources to help you advance as a " + selectedCareer;
        tvLearnMoreDescription.setText(customMessage);
    }
    
    private void openCareerResourceUrl() {
        String url = "";
        

        switch (selectedCareer) {
            case UserResponses.DEVELOPER:
                url = getString(R.string.url_developer);
                break;
            case UserResponses.NETWORK_SPECIALIST:
                url = getString(R.string.url_network_specialist);
                break;
            case UserResponses.IT_SUPPORT:
                url = getString(R.string.url_it_support);
                break;
            case UserResponses.DATA_ANALYTICS:
                url = getString(R.string.url_data_analytics);
                break;
            case UserResponses.UI_DESIGNER:
                url = getString(R.string.url_ui_designer);
                break;
            case UserResponses.PROJECT_MANAGER:
                url = getString(R.string.url_project_manager);
                break;
            case UserResponses.CYBER_SECURITY:
                url = getString(R.string.url_cyber_security);
                break;
            default:
                url = getString(R.string.url_developer); // Default to developer resources
        }
        

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
        startActivity(browserIntent);
    }
    
    private void updateCareerTip(String career) {
        // Get either a random tip or one of the motivational quotes
        String tip = careerTipManager.getRandomTipForCareer(career);
        
        // Make the text style match the content
        if (tip.startsWith("💻") || tip.startsWith("🌐") || tip.startsWith("🖥") || 
            tip.startsWith("📊") || tip.startsWith("🎨") || tip.startsWith("📅") || 
            tip.startsWith("🔐")) {
            // This is a quote, style it differently (optional)
            tvCareerTip.setTextSize(15); // Slightly larger
        } else {
            // This is a regular tip
            tvCareerTip.setTextSize(14); // Regular size
        }
        
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

        int completedTasks = taskManager.getCompletedTaskCount();
        int totalTasks = taskManager.getTotalTaskCount();
        tvTasksCompleted.setText(completedTasks + "/" + totalTasks);
        

        int todayGoals = goalManager.getActiveTodayGoalsCount();
        tvGoalsToday.setText(String.valueOf(todayGoals));
        

        int taskProgress = totalTasks > 0 ? (completedTasks * 100) / totalTasks : 0;
        int completedGoals = goalManager.getCompletedGoals().size();
        int totalGoals = goalManager.getAllGoals().size();
        int goalProgress = totalGoals > 0 ? (completedGoals * 100) / totalGoals : 0;
        

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
        

        overallProgressBar.setProgress(overallProgress);
        tvOverallProgress.setText(overallProgress + "% Complete");
    }
    
    @Override
    protected void onResume() {
        super.onResume();

        updateStatistics();
        

        String updatedCareer = userManager.getSelectedCareer();
        if (!updatedCareer.equals(selectedCareer)) {
            selectedCareer = updatedCareer;
            setupLearnMoreSection();
        }
    }
}
