package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class input_name extends AppCompatActivity {
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_name);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UserManager
        userManager = new UserManager(this);
        
        // Check if user is already logged in
        if (userManager.isLoggedIn()) {
            navigateToHomePage();
            return;
        }

        EditText etName = findViewById(R.id.et_name);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString().trim();

                if (!userName.isEmpty()) {
                    // Save username in SharedPreferences
                    userManager.saveUserName(userName);
                    navigateToHomePage();
                } else {
                    Toast.makeText(input_name.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void navigateToHomePage() {
        Intent intent = new Intent(input_name.this, home_page.class);
        startActivity(intent);
        finish();
    }
}
