package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class input_name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        EditText etName = findViewById(R.id.et_name);
        Button btnConfirm = findViewById(R.id.btn_confirm);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString().trim();

                if (!userName.isEmpty()) {

                    Intent intent = new Intent(input_name.this, home_page.class);
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(input_name.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
