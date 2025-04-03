package com.example.nextstep;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class result_page extends AppCompatActivity {

    private TextView tvDeveloper, tvNetworkSpecialist, tvITSupport,
            tvDataAnalytics, tvUIDesigner, tvProjectManager,
            tvCyberSecurity, tvRecommendedCareer;
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

        tvDeveloper = findViewById(R.id.tvDeveloper);
        tvNetworkSpecialist = findViewById(R.id.tvNetworkSpecialist);
        tvITSupport = findViewById(R.id.tvITSupport);
        tvDataAnalytics = findViewById(R.id.tvDataAnalytics);
        tvUIDesigner = findViewById(R.id.tvUIDesigner);
        tvProjectManager = findViewById(R.id.tvProjectManager);
        tvCyberSecurity = findViewById(R.id.tvCyberSecurity);
        tvRecommendedCareer = findViewById(R.id.tvRecommendedCareer);

        displayResults();
    }
    private void displayResults() {
        UserResponses userResponses = new UserResponses(this);
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
    }
}