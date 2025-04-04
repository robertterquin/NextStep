package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class task_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_page);

        // Apply window insets to handle system bars (like status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigation ImageViews
        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navTasks = findViewById(R.id.nav_tasks);
        ImageView navGoals = findViewById(R.id.nav_goals);
        ImageView navProfile = findViewById(R.id.nav_profile);

        // Strike-through Views for active state indicator
        View strikeThroughHome = findViewById(R.id.strike_home);
        View strikeThroughTasks = findViewById(R.id.strike_tasks);
        View strikeThroughGoals = findViewById(R.id.strike_goals);
        View strikeThroughProfile = findViewById(R.id.strike_profile);

        // Set initial state for Task (assuming the user is on the Task page)
        strikeThroughHome.setVisibility(View.INVISIBLE);  // Home is inactive
        strikeThroughTasks.setVisibility(View.VISIBLE);  // Tasks is active
        strikeThroughGoals.setVisibility(View.INVISIBLE);  // Goals is inactive
        strikeThroughProfile.setVisibility(View.INVISIBLE);  // Profile is inactive

        // Set click listeners for navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Home page and update active state
                Intent homeIntent = new Intent(task_page.this, home_page.class);
                startActivity(homeIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

                // Update visibility for strike-through
                strikeThroughHome.setVisibility(View.VISIBLE);  // Home active
                strikeThroughTasks.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughGoals.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughProfile.setVisibility(View.INVISIBLE);  // Inactive
            }
        });

        navTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stay on the current page (Task)
                strikeThroughHome.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughTasks.setVisibility(View.VISIBLE);  // Tasks active
                strikeThroughGoals.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughProfile.setVisibility(View.INVISIBLE);  // Inactive
            }
        });

        navGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Goals page and update active state
                Intent goalsIntent = new Intent(task_page.this, goals_page.class);
                startActivity(goalsIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

                // Update visibility for strike-through
                strikeThroughHome.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughTasks.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughGoals.setVisibility(View.VISIBLE);  // Goals active
                strikeThroughProfile.setVisibility(View.INVISIBLE);  // Inactive
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Profile page and update active state
                Intent profileIntent = new Intent(task_page.this, profile_page.class);
                startActivity(profileIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

                // Update visibility for strike-through
                strikeThroughHome.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughTasks.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughGoals.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughProfile.setVisibility(View.VISIBLE);  // Profile active
            }
        });
    }
}
