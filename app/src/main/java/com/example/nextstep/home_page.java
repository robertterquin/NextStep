package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home_page extends AppCompatActivity {

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

        // Strike-through Views for active state indicator
        View strikeThroughHome = findViewById(R.id.strike_home);
        View strikeThroughTasks = findViewById(R.id.strike_tasks);
        View strikeThroughGoals = findViewById(R.id.strike_goals);
        View strikeThroughProfile = findViewById(R.id.strike_profile);

        // Set initial state for Home (assuming the user is on the Home page)
        strikeThroughHome.setVisibility(View.VISIBLE);  // Home is active
        strikeThroughTasks.setVisibility(View.INVISIBLE);  // Inactive
        strikeThroughGoals.setVisibility(View.INVISIBLE);  // Inactive
        strikeThroughProfile.setVisibility(View.INVISIBLE);  // Inactive

        // Set click listeners for navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Keep Home active (strike-through stays for Home)
                strikeThroughHome.setVisibility(View.VISIBLE);
                strikeThroughTasks.setVisibility(View.INVISIBLE);
                strikeThroughGoals.setVisibility(View.INVISIBLE);
                strikeThroughProfile.setVisibility(View.INVISIBLE);
            }
        });

        navTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Task page and update active state
                Intent taskIntent = new Intent(home_page.this, task_page.class);
                startActivity(taskIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

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
                Intent goalsIntent = new Intent(home_page.this, goals_page.class);
                startActivity(goalsIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

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
                Intent profileIntent = new Intent(home_page.this, profile_page.class);
                startActivity(profileIntent);
                overridePendingTransition(0, 0); // Optional: To disable the transition animation

                strikeThroughHome.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughTasks.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughGoals.setVisibility(View.INVISIBLE);  // Inactive
                strikeThroughProfile.setVisibility(View.VISIBLE);  // Profile active
            }
        });
    }
}
