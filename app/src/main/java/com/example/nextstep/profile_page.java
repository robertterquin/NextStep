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

    // ActivityResultLauncher to open the gallery
    private final ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        try {
                            // Get the URI of the selected image
                            InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            // Store the image as Base64 string in SharedPreferences
                            storeProfileImage(bitmap);
                            // Set the profile image to the ImageView
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

        // Display user info
        displayUserInfo();

        // Set onClickListener for the profile image (instead of the button)
        profilePicture.setOnClickListener(v -> openGallery());

        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            navigateToInputName();
        });

        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.PROFILE);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryResultLauncher.launch(intent);
    }

    private void storeProfileImage(Bitmap bitmap) {
        // Convert the Bitmap to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convert the byte array to Base64 string
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Save to SharedPreferences
        userManager.saveProfilePicture(encodedImage);
    }

    private void displayUserInfo() {
        String username = userManager.getUserName();
        String selectedCareer = userManager.getSelectedCareer();

        // Set username and career path
        tvUsername.setText(username);
        tvSelectedCareer.setText("Selected Career Path: " + selectedCareer);

        // Load and display the profile image if it exists in SharedPreferences
        String encodedImage = userManager.getProfilePicture();
        if (encodedImage != null) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profilePicture.setImageBitmap(decodedBitmap);
        }
    }

    private void navigateToInputName() {
        Intent intent = new Intent(profile_page.this, input_name.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
