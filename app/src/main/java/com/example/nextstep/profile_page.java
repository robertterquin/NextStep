package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profile_page extends AppCompatActivity {
    private UserManager userManager;
    private TextView tvUsername, tvSelectedCareer;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UserManager
        userManager = new UserManager(this);
        
        // Initialize views
        tvUsername = findViewById(R.id.tvUsername);
        tvSelectedCareer = findViewById(R.id.tvSelectedCareer);
        btnLogout = findViewById(R.id.btnLogout);
        
        // Display user information
        displayUserInfo();
        
        // Setup logout button
        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            navigateToInputName();
        });

        // Setup bottom navigation using the helper
        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.PROFILE);
    }
    
    private void displayUserInfo() {
        String username = userManager.getUserName();
        String selectedCareer = userManager.getSelectedCareer();
        
        tvUsername.setText(username);
        tvSelectedCareer.setText("Selected Career Path: " + selectedCareer);
    }
    
    private void navigateToInputName() {
        Intent intent = new Intent(profile_page.this, input_name.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
