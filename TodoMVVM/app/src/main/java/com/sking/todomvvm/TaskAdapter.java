package com.sking.todomvvm;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);


        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        final Task currentTask = tasks.get(position);
        if(currentTask.isIs_completed()){
            holder.textViewTitle.setPaintFlags(holder.textViewTitle.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else{
            holder.textViewTitle.setPaintFlags(0);
        }
        holder.textViewTitle.setText(currentTask.getTitle());
        holder.textViewDescription.setText(currentTask.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentTask.getPriority()));

        holder.checkTask.setChecked(currentTask.isIs_completed());


        holder.checkTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentTask.setIs_completed(isChecked);
            }
        });




    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {

        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private CheckBox checkTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            checkTask = itemView.findViewById(R.id.task_check);





            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(tasks.get(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
