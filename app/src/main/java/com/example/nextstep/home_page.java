package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Greeting Text
        TextView greetingText = findViewById(R.id.greeting_text);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        if (userName != null && !userName.isEmpty()) {
            greetingText.setText("Good Evening, " + userName);
        }

        // Navigation ImageViews
        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navTasks = findViewById(R.id.nav_tasks);
        ImageView navGoals = findViewById(R.id.nav_goals);
        ImageView navProfile = findViewById(R.id.nav_profile);

        // Set initial active state for Home (assuming the user is on the Home page)
        navHome.setImageResource(R.drawable.home_icon_active);
        navTasks.setImageResource(R.drawable.checked); // Inactive state
        navGoals.setImageResource(R.drawable.target); // Inactive state
        navProfile.setImageResource(R.drawable.userrr); // Inactive state

        // Set click listeners for navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stay on the current page (Home), no need to navigate anywhere
            }
        });

        navTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Task page and set the appropriate highlighted state
                Intent taskIntent = new Intent(home_page.this, task_page.class);
                startActivity(taskIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation
            }
        });

        navGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Goals page and set the appropriate highlighted state
                Intent goalsIntent = new Intent(home_page.this, goals_page.class);
                startActivity(goalsIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Profile page and set the appropriate highlighted state
                Intent profileIntent = new Intent(home_page.this, profile_page.class);
                startActivity(profileIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation
            }
        });
    }
}
