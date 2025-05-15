package com.example.nextstep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class profile_page extends AppCompatActivity {
    private UserManager userManager;
    private TextView tvUsername, tvSelectedCareer;
    private Button btnLogout;
    private ImageView profilePicture;

    private final ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            storeProfileImage(bitmap);
                            profilePicture.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        userManager = new UserManager(this);

        tvUsername = findViewById(R.id.tvUsername);
        tvSelectedCareer = findViewById(R.id.tvSelectedCareer);
        btnLogout = findViewById(R.id.btnLogout);
        profilePicture = findViewById(R.id.profile_picture);

        displayUserInfo();

        profilePicture.setOnClickListener(v -> openGallery());

        btnLogout.setOnClickListener(v -> {
            userManager.logout();  // Clear SharedPreferences & DB data
            Intent intent = new Intent(profile_page.this, input_name.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.PROFILE);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryResultLauncher.launch(intent);
    }

    private void storeProfileImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        userManager.saveProfilePicture(encodedImage);

        // Also update DB profile picture
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
        dbHelper.updateProfilePic(encodedImage);
    }

    private void displayUserInfo() {
        String username = userManager.getUserName();
        String career = userManager.getSelectedCareer();
        String encodedImage = userManager.getProfilePicture();

        tvUsername.setText(username != null && !username.isEmpty() ? username : "No username set");
        tvSelectedCareer.setText(career != null && !career.isEmpty() ? career : "No career selected");

        if (encodedImage != null && !encodedImage.isEmpty()) {
            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profilePicture.setImageBitmap(bitmap);
        } else {
            profilePicture.setImageResource(R.drawable.empty_profile);
            // Replace with your app’s default profile image resource ID
        }
    }
}