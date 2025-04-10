package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class result_page extends AppCompatActivity {

    private TextView tvDeveloper, tvNetworkSpecialist, tvITSupport,
            tvDataAnalytics, tvUIDesigner, tvProjectManager,
            tvCyberSecurity, tvRecommendedCareer;
    private CardView cvDeveloper, cvNetworkSpecialist, cvITSupport,
            cvDataAnalytics, cvUIDesigner, cvProjectManager,
            cvCyberSecurity;
    private Button btnContinue;
    private UserManager userManager;
    private UserResponses userResponses;
    private String selectedCareer = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UserManager
        userManager = new UserManager(this);
        userResponses = new UserResponses(this);

        // Initialize views
        initializeViews();
        
        // Setup clickable cards
        setupCareerCardListeners();
        
        // Display results
        displayResults();
        
        // Set continue button click listener
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedCareer.isEmpty()) {
                    userManager.saveSelectedCareer(selectedCareer);
                    
                    // Check if user is already logged in
                    if (userManager.isLoggedIn()) {
                        // User already has an account, go directly to home page
                        navigateToHomePage();
                    } else {
                        // User needs to enter their name
                        Intent intent = new Intent(result_page.this, input_name.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(result_page.this, "Please select a career path", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void initializeViews() {
        tvDeveloper = findViewById(R.id.tvDeveloper);
        tvNetworkSpecialist = findViewById(R.id.tvNetworkSpecialist);
        tvITSupport = findViewById(R.id.tvITSupport);
        tvDataAnalytics = findViewById(R.id.tvDataAnalytics);
        tvUIDesigner = findViewById(R.id.tvUIDesigner);
        tvProjectManager = findViewById(R.id.tvProjectManager);
        tvCyberSecurity = findViewById(R.id.tvCyberSecurity);
        tvRecommendedCareer = findViewById(R.id.tvRecommendedCareer);
        
        // Get CardView parent containers
        cvDeveloper = (CardView) tvDeveloper.getParent();
        cvNetworkSpecialist = (CardView) tvNetworkSpecialist.getParent();
        cvITSupport = (CardView) tvITSupport.getParent();
        cvDataAnalytics = (CardView) tvDataAnalytics.getParent();
        cvUIDesigner = (CardView) tvUIDesigner.getParent();
        cvProjectManager = (CardView) tvProjectManager.getParent();
        cvCyberSecurity = (CardView) tvCyberSecurity.getParent();
        
        // Add continue button
        btnContinue = findViewById(R.id.btnContinue);
    }
    
    private void setupCareerCardListeners() {
        cvDeveloper.setOnClickListener(v -> selectCareer(UserResponses.DEVELOPER, cvDeveloper));
        cvNetworkSpecialist.setOnClickListener(v -> selectCareer(UserResponses.NETWORK_SPECIALIST, cvNetworkSpecialist));
        cvITSupport.setOnClickListener(v -> selectCareer(UserResponses.IT_SUPPORT, cvITSupport));
        cvDataAnalytics.setOnClickListener(v -> selectCareer(UserResponses.DATA_ANALYTICS, cvDataAnalytics));
        cvUIDesigner.setOnClickListener(v -> selectCareer(UserResponses.UI_DESIGNER, cvUIDesigner));
        cvProjectManager.setOnClickListener(v -> selectCareer(UserResponses.PROJECT_MANAGER, cvProjectManager));
        cvCyberSecurity.setOnClickListener(v -> selectCareer(UserResponses.CYBER_SECURITY, cvCyberSecurity));
    }
    
    private void selectCareer(String career, CardView selectedCard) {
        // Reset all cards
        resetAllCardBackgrounds();
        
        // Highlight selected card
        selectedCard.setCardBackgroundColor(getResources().getColor(R.color.light_blue));
        
        // Save selected career
        selectedCareer = career;
        Toast.makeText(this, career + " selected", Toast.LENGTH_SHORT).show();
    }
    
    private void resetAllCardBackgrounds() {
        cvDeveloper.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvNetworkSpecialist.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvITSupport.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvDataAnalytics.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvUIDesigner.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvProjectManager.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cvCyberSecurity.setCardBackgroundColor(getResources().getColor(android.R.color.white));
    }
    
    private void displayResults() {
        Map<String, Integer> percentages = userResponses.calculatePercentages();
        String recommendedCareer = userResponses.getRecommendedCareer();

        // Set text for each career with percentage
        tvDeveloper.setText(String.format("Developer\n%d%%",
                percentages.get(UserResponses.DEVELOPER)));

        tvNetworkSpecialist.setText(String.format("Network\nSpecialist\n%d%%",
                percentages.get(UserResponses.NETWORK_SPECIALIST)));

        tvITSupport.setText(String.format("IT Support &\nAdministration\n%d%%",
                percentages.get(UserResponses.IT_SUPPORT)));

        tvDataAnalytics.setText(String.format("Data &\nAnalytics\n%d%%",
                percentages.get(UserResponses.DATA_ANALYTICS)));

        tvUIDesigner.setText(String.format("UI Designer\n%d%%",
                percentages.get(UserResponses.UI_DESIGNER)));

        tvProjectManager.setText(String.format("Project Manager\n%d%%",
                percentages.get(UserResponses.PROJECT_MANAGER)));

        tvCyberSecurity.setText(String.format("Cyber Security\n%d%%",
                percentages.get(UserResponses.CYBER_SECURITY)));

        // Display the recommended career
        tvRecommendedCareer.setText(recommendedCareer);
        
        // Auto-select the recommended career
        switch (recommendedCareer) {
            case UserResponses.DEVELOPER:
                selectCareer(UserResponses.DEVELOPER, cvDeveloper);
                break;
            case UserResponses.NETWORK_SPECIALIST:
                selectCareer(UserResponses.NETWORK_SPECIALIST, cvNetworkSpecialist);
                break;
            case UserResponses.IT_SUPPORT:
                selectCareer(UserResponses.IT_SUPPORT, cvITSupport);
                break;
            case UserResponses.DATA_ANALYTICS:
                selectCareer(UserResponses.DATA_ANALYTICS, cvDataAnalytics);
                break;
            case UserResponses.UI_DESIGNER:
                selectCareer(UserResponses.UI_DESIGNER, cvUIDesigner);
                break;
            case UserResponses.PROJECT_MANAGER:
                selectCareer(UserResponses.PROJECT_MANAGER, cvProjectManager);
                break;
            case UserResponses.CYBER_SECURITY:
                selectCareer(UserResponses.CYBER_SECURITY, cvCyberSecurity);
                break;
        }
    }
    
    // Add method to navigate to home page
    private void navigateToHomePage() {
        Intent intent = new Intent(result_page.this, home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}