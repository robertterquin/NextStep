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

public class goals_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goals_page);

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
        View strikeHome = findViewById(R.id.strike_home);
        View strikeTasks = findViewById(R.id.strike_tasks);
        View strikeGoals = findViewById(R.id.strike_goals);
        View strikeProfile = findViewById(R.id.strike_profile);

        // Set initial state for Goals (active tab)
        strikeHome.setVisibility(View.INVISIBLE);
        strikeTasks.setVisibility(View.INVISIBLE);
        strikeGoals.setVisibility(View.VISIBLE);
        strikeProfile.setVisibility(View.INVISIBLE);

        // Navigation click listeners
        navHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(goals_page.this, home_page.class);
            startActivity(homeIntent);
            overridePendingTransition(0, 0);
        });

        navTasks.setOnClickListener(v -> {
            Intent tasksIntent = new Intent(goals_page.this, task_page.class);
            startActivity(tasksIntent);
            overridePendingTransition(0, 0);
        });

        navGoals.setOnClickListener(v -> {
            // Already on goals_page – do nothing or reload if needed
        });

        navProfile.setOnClickListener(v -> {
            Intent profileIntent = new Intent(goals_page.this, profile_page.class);
            startActivity(profileIntent);
            overridePendingTransition(0, 0);
        });
    }
}
