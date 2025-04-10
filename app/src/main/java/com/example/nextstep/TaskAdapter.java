package com.example.nextstep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    
    private List<Task> tasks;
    private TaskManager taskManager;
    private OnTaskCompletionListener listener;
    
    public interface OnTaskCompletionListener {
        void onTaskCompletionChanged(int completedCount, int totalCount);
    }
    
    public TaskAdapter(List<Task> tasks, TaskManager taskManager, OnTaskCompletionListener listener) {
        this.tasks = tasks;
        this.taskManager = taskManager;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskText.setText(task.getDescription());
        holder.taskCheckbox.setChecked(task.isCompleted());
        
        holder.taskCheckbox.setOnClickListener(v -> {
            boolean isChecked = holder.taskCheckbox.isChecked();
            task.setCompleted(isChecked);
            taskManager.setTaskCompleted(task.getId(), isChecked);
            
            if (listener != null) {
                int completedCount = taskManager.getCompletedTaskCount();
                int totalCount = taskManager.getTotalTaskCount();
                listener.onTaskCompletionChanged(completedCount, totalCount);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return tasks.size();
    }
    
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox taskCheckbox;
        TextView taskText;
        
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskCheckbox = itemView.findViewById(R.id.taskCheckbox);
            taskText = itemView.findViewById(R.id.taskText);
        }
    }
    
    public void updateTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }
}
