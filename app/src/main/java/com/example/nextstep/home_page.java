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

        // Greeting text setup
        TextView greetingText = findViewById(R.id.greeting_text);
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            greetingText.setText("Good Evening, " + userName);
        }

        // Navigation ImageViews
        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navTasks = findViewById(R.id.nav_tasks);
        ImageView navGoals = findViewById(R.id.nav_goals);
        ImageView navProfile = findViewById(R.id.nav_profile);

        // Strike-through Views
        View strikeHome = findViewById(R.id.strike_home);
        View strikeTasks = findViewById(R.id.strike_tasks);
        View strikeGoals = findViewById(R.id.strike_goals);
        View strikeProfile = findViewById(R.id.strike_profile);

        // Set current page as active (Home)
        strikeHome.setVisibility(View.VISIBLE);
        strikeTasks.setVisibility(View.INVISIBLE);
        strikeGoals.setVisibility(View.INVISIBLE);
        strikeProfile.setVisibility(View.INVISIBLE);

        // Navigation click listeners
        navHome.setOnClickListener(v -> {
            // Already on home page – do nothing
        });

        navTasks.setOnClickListener(v -> {
            startActivity(new Intent(home_page.this, task_page.class));
            overridePendingTransition(0, 0);
        });

        navGoals.setOnClickListener(v -> {
            startActivity(new Intent(home_page.this, goals_page.class));
            overridePendingTransition(0, 0);
        });

        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(home_page.this, profile_page.class));
            overridePendingTransition(0, 0);
        });
    }
}
