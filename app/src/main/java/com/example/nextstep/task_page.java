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

        // Set the active state for the "Tasks" icon (current page)
        navHome.setImageResource(R.drawable.home_icon); // Inactive state
        navTasks.setImageResource(R.drawable.checked_active); // Active state (Task page)
        navGoals.setImageResource(R.drawable.target); // Inactive state
        navProfile.setImageResource(R.drawable.userrr); // Inactive state

        // Home Page Intent
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Home page and set active state for Home
                Intent homeIntent = new Intent(task_page.this, home_page.class);
                startActivity(homeIntent);
                finish(); // Close the Task page
            }
        });

        // Tasks Page Intent (Reloads the same page, no action needed)
        navTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stay on the current page (Tasks)
            }
        });

        // Goals Page Intent
        navGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Goals page and set active state for Goals
                Intent goalsIntent = new Intent(task_page.this, goals_page.class);
                startActivity(goalsIntent);
                finish(); // Close the Task page
            }
        });

        // Profile Page Intent
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Profile page and set active state for Profile
                Intent profileIntent = new Intent(task_page.this, profile_page.class);
                startActivity(profileIntent);
                finish(); // Close the Task page
            }
        });
    }
}
