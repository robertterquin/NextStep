package com.example.nextstep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    
    private List<Goal> goals;
    private Context context;
    private GoalManager goalManager;
    private OnGoalListener onGoalListener;
    
    public interface OnGoalListener {
        void onGoalClick(Goal goal, int position);
        void onGoalCompleted(Goal goal, int position);
        void onGoalDeleted(Goal goal, int position);
    }
    
    public GoalAdapter(Context context, List<Goal> goals, GoalManager goalManager, OnGoalListener onGoalListener) {
        this.context = context;
        this.goals = goals;
        this.goalManager = goalManager;
        this.onGoalListener = onGoalListener;
    }
    
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goals.get(position);
        
        // Bind data to views
        holder.tvGoalTitle.setText(goal.getTitle());
        holder.tvGoalDesc.setText(goal.getDescription());
        holder.tvGoalDates.setText(
                "Start: " + GoalManager.formatDate(goal.getStartDate()) + 
                " • End: " + GoalManager.formatDate(goal.getEndDate())
        );
        
        // Set checkbox state
        holder.cbGoalCompleted.setChecked(goal.isCompleted());
        
        // Setup click listeners
        holder.itemView.setOnClickListener(v -> {
            if (onGoalListener != null) {
                onGoalListener.onGoalClick(goal, holder.getAdapterPosition());
            }
        });
        
        holder.cbGoalCompleted.setOnClickListener(v -> {
            boolean isChecked = holder.cbGoalCompleted.isChecked();
            goal.setCompleted(isChecked);
            goalManager.saveGoal(goal);
            if (onGoalListener != null) {
                onGoalListener.onGoalCompleted(goal, holder.getAdapterPosition());
            }
        });
        
        holder.btnDelete.setOnClickListener(v -> {
            if (onGoalListener != null) {
                onGoalListener.onGoalDeleted(goal, holder.getAdapterPosition());
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return goals.size();
    }
    
    public void updateGoals(List<Goal> newGoals) {
        this.goals = newGoals;
        notifyDataSetChanged();
    }
    
    static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView tvGoalTitle, tvGoalDesc, tvGoalDates;
        CheckBox cbGoalCompleted;
        ImageButton btnDelete;
        
        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGoalTitle = itemView.findViewById(R.id.tvGoalTitle);
            tvGoalDesc = itemView.findViewById(R.id.tvGoalDescription);
            tvGoalDates = itemView.findViewById(R.id.tvGoalDates);
            cbGoalCompleted = itemView.findViewById(R.id.cbGoalCompleted);
            btnDelete = itemView.findViewById(R.id.btnDeleteGoal);
        }
    }
}
