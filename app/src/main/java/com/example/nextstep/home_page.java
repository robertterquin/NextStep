package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView greetingText = findViewById(R.id.greeting_text);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        if (userName != null && !userName.isEmpty()) {
            greetingText.setText("Good Evening, " + userName);
        }
    }
}
