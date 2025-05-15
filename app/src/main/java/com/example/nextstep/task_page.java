package com.example.nextstep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class task_page extends AppCompatActivity implements TaskAdapter.OnTaskCompletionListener {

    private UserManager userManager;
    private TaskManager taskManager;
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private TextView careerPathTitle;
    private TextView progressText;
    private ProgressBar taskProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize managers
        userManager = new UserManager(this);
        String selectedCareer = userManager.getSelectedCareer();
        if (selectedCareer.isEmpty()) {
            // If no career is selected, default to Developer
            selectedCareer = UserResponses.DEVELOPER;
        }
        taskManager = new TaskManager(this, selectedCareer);

        // Initialize views
        careerPathTitle = findViewById(R.id.careerPathTitle);
        progressText = findViewById(R.id.progressText);
        taskProgressBar = findViewById(R.id.taskProgressBar);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);

        // Set career path title
        careerPathTitle.setText("Your Career Path: " + selectedCareer);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);
        
        // Add dividers between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(taskRecyclerView.getContext(), layoutManager.getOrientation());
        taskRecyclerView.addItemDecoration(dividerItemDecoration);
        
        List<Task> tasks = taskManager.getTasksForCareer();
        taskAdapter = new TaskAdapter(tasks, taskManager, this);
        taskRecyclerView.setAdapter(taskAdapter);

        // Update progress display
        updateProgressDisplay();

        // Setup bottom navigation using the helper
        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.TASKS);
    }

    @Override
    public void onTaskCompletionChanged(int completedCount, int totalCount) {
        updateProgressDisplay();
    }

    private void updateProgressDisplay() {
        int completedCount = taskManager.getCompletedTaskCount();
        int totalCount = taskManager.getTotalTaskCount();
        

        progressText.setText(completedCount + "/" + totalCount);
        

        int progressPercentage = (totalCount > 0) ? (completedCount * 100) / totalCount : 0;
        taskProgressBar.setProgress(progressPercentage);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String selectedCareer = userManager.getSelectedCareer();
        if (selectedCareer.isEmpty()) {
            selectedCareer = UserResponses.DEVELOPER;
        }
        
        // Check if career changed and update if needed
        if (!taskManager.careerPath.equals(selectedCareer)) {
            taskManager = new TaskManager(this, selectedCareer);
            careerPathTitle.setText("Your Career Path: " + selectedCareer);
            List<Task> tasks = taskManager.getTasksForCareer();
            taskAdapter = new TaskAdapter(tasks, taskManager, this);
            taskRecyclerView.setAdapter(taskAdapter);
        }

        updateProgressDisplay();
    }
}
