package com.example.nextstep;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class goals_page extends AppCompatActivity implements GoalAdapter.OnGoalListener {

    private RecyclerView goalsRecyclerView;
    private TextView tvNoGoals, tvActiveGoals, tvCompletedGoals;
    private FloatingActionButton fabAddGoal;
    private GoalManager goalManager;
    private GoalAdapter goalAdapter;
    private boolean showingActiveGoals = true;

    // For date picking
    private Date startDate = null;
    private Date endDate = null;
    private TextView tvDateRange;

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

        // Initialize views
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);
        tvNoGoals = findViewById(R.id.tvNoGoals);
        fabAddGoal = findViewById(R.id.fabAddGoal);
        tvActiveGoals = findViewById(R.id.tvActiveGoals);
        tvCompletedGoals = findViewById(R.id.tvCompletedGoals);

        // Initialize goal manager
        goalManager = new GoalManager(this);

        // Set up RecyclerView
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Add spacing decoration
        goalsRecyclerView.addItemDecoration(new SpacingItemDecoration(8));

        // Load active goals by default
        loadActiveGoals();

        // Setup tab switching
        tvActiveGoals.setOnClickListener(v -> {
            showingActiveGoals = true;
            updateTabSelection();
            loadActiveGoals();
        });

        tvCompletedGoals.setOnClickListener(v -> {
            showingActiveGoals = false;
            updateTabSelection();
            loadCompletedGoals();
        });

        // Setup FAB click listener
        fabAddGoal.setOnClickListener(v -> showAddGoalDialog(null));

        // Setup bottom navigation using the helper
        NavigationHelper.setupBottomNavigation(this, NavigationHelper.NavigationTab.GOALS);
    }

    private void updateTabSelection() {
        if (showingActiveGoals) {
            tvActiveGoals.setTextColor(getResources().getColor(R.color.black));
            tvActiveGoals.setBackground(getResources().getDrawable(R.drawable.selected_tab_bg));
            tvCompletedGoals.setTextColor(getResources().getColor(R.color.gray));
            tvCompletedGoals.setBackground(null);
        } else {
            tvCompletedGoals.setTextColor(getResources().getColor(R.color.black));
            tvCompletedGoals.setBackground(getResources().getDrawable(R.drawable.selected_tab_bg));
            tvActiveGoals.setTextColor(getResources().getColor(R.color.gray));
            tvActiveGoals.setBackground(null);
        }
    }

    private void loadActiveGoals() {
        List<Goal> activeGoals = goalManager.getActiveGoals();
        updateGoalsList(activeGoals);
    }

    private void loadCompletedGoals() {
        List<Goal> completedGoals = goalManager.getCompletedGoals();
        updateGoalsList(completedGoals);
    }

    private void updateGoalsList(List<Goal> goals) {
        if (goals.isEmpty()) {
            tvNoGoals.setVisibility(View.VISIBLE);
            goalsRecyclerView.setVisibility(View.GONE);
        } else {
            tvNoGoals.setVisibility(View.GONE);
            goalsRecyclerView.setVisibility(View.VISIBLE);

            if (goalAdapter == null) {
                goalAdapter = new GoalAdapter(this, goals, goalManager, this);
                goalsRecyclerView.setAdapter(goalAdapter);
            } else {
                goalAdapter.updateGoals(goals);
            }
        }
    }

    private void showAddGoalDialog(Goal existingGoal) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_goal);
        dialog.setCancelable(true);

        // Set dialog window properties
        dialog.getWindow().setLayout(
                android.view.WindowManager.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT
        );
        dialog.getWindow().setGravity(android.view.Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        // Initialize dialog views
        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        EditText etGoalTitle = dialog.findViewById(R.id.etGoalTitle);
        EditText etGoalDescription = dialog.findViewById(R.id.etGoalDescription);
        Button btnStartDate = dialog.findViewById(R.id.btnStartDate);
        Button btnEndDate = dialog.findViewById(R.id.btnEndDate);
        tvDateRange = dialog.findViewById(R.id.tvDateRange);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        // Set title based on whether we're editing or adding
        boolean isEditing = existingGoal != null;
        dialogTitle.setText(isEditing ? "Edit Goal" : "Add New Goal");

        // If editing, populate fields with existing goal data
        if (isEditing) {
            etGoalTitle.setText(existingGoal.getTitle());
            etGoalDescription.setText(existingGoal.getDescription());
            startDate = existingGoal.getStartDate();
            endDate = existingGoal.getEndDate();
            updateDateRangeText();
        } else {
            startDate = null;
            endDate = null;
        }

        // Setup date pickers
        btnStartDate.setOnClickListener(v -> showDatePicker(true));
        btnEndDate.setOnClickListener(v -> showDatePicker(false));

        // Setup button click listeners
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String title = etGoalTitle.getText().toString().trim();
            String description = etGoalDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(goals_page.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (startDate == null || endDate == null) {
                Toast.makeText(goals_page.this, "Please select both start and end dates", Toast.LENGTH_SHORT).show();
                return;
            }

            if (startDate.after(endDate)) {
                Toast.makeText(goals_page.this, "Start date must be before end date", Toast.LENGTH_SHORT).show();
                return;
            }

            Goal goal;
            if (isEditing) {
                goal = existingGoal;
                goal.setTitle(title);
                goal.setDescription(description);
                goal.setStartDate(startDate);
                goal.setEndDate(endDate);
            } else {
                goal = new Goal(title, description, startDate, endDate);
            }

            goalManager.saveGoal(goal);
            
            // Refresh the list
            if (showingActiveGoals) {
                loadActiveGoals();
            } else {
                loadCompletedGoals();
            }
            
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showDatePicker(boolean isStartDate) {
        final Calendar calendar = Calendar.getInstance();
        if (isStartDate && startDate != null) {
            calendar.setTime(startDate);
        } else if (!isStartDate && endDate != null) {
            calendar.setTime(endDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    if (isStartDate) {
                        startDate = calendar.getTime();
                        if (endDate != null && startDate.after(endDate)) {
                            endDate = null;
                        }
                    } else {
                        endDate = calendar.getTime();
                        if (startDate != null && endDate.before(startDate)) {
                            startDate = null;
                        }
                    }
                    updateDateRangeText();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void updateDateRangeText() {
        if (tvDateRange != null) {
            if (startDate != null && endDate != null) {
                String dateRange = "From: " + GoalManager.formatDate(startDate) +
                        "\nTo: " + GoalManager.formatDate(endDate);
                tvDateRange.setText(dateRange);
            } else if (startDate != null) {
                tvDateRange.setText("From: " + GoalManager.formatDate(startDate) + "\nEnd date not set");
            } else if (endDate != null) {
                tvDateRange.setText("Start date not set\nTo: " + GoalManager.formatDate(endDate));
            } else {
                tvDateRange.setText("No dates selected");
            }
        }
    }

    @Override
    public void onGoalClick(Goal goal, int position) {
        showAddGoalDialog(goal);
    }

    @Override
    public void onGoalCompleted(Goal goal, int position) {
        // When a goal is marked completed/uncompleted, refresh lists
        if (showingActiveGoals && goal.isCompleted()) {
            loadActiveGoals();
        } else if (!showingActiveGoals && !goal.isCompleted()) {
            loadCompletedGoals();
        }
    }

    @Override
    public void onGoalDeleted(Goal goal, int position) {
        goalManager.deleteGoal(goal.getId());
        if (showingActiveGoals) {
            loadActiveGoals();
        } else {
            loadCompletedGoals();
        }
        Toast.makeText(this, "Goal deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh goals list when returning to the screen
        if (showingActiveGoals) {
            loadActiveGoals();
        } else {
            loadCompletedGoals();
        }
    }
}
