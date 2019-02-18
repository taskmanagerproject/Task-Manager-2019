package com.example.nikhil.taskmanager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.taskmanager.model.Tasks;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder> {
    protected Context mContext;
    private List<Tasks> mData;
    public MyTaskAdapter(Context context, List<Tasks> data){
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_task_recy_view_items,viewGroup, false);
        return new MyTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Tasks t = mData.get(i);
        Log.d(TAG,"Values in my task "+t.getTitle());
        Log.d(TAG,"Values in my task "+t.getDescription());
        myViewHolder.taskTitle.setText(t.getTitle());
        myViewHolder.taskDescription.setText(t.getDescription());
        myViewHolder.taskAssignee.setText(t.getAssignTo());
        myViewHolder.taskPriority.setText(t.getPriority());
        if (t.getPriority().equals("Low")){
            myViewHolder.priorityViewColor.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        else if(t.getPriority().equals("Medium")){
            myViewHolder.priorityViewColor.setBackgroundColor(Color.parseColor("#FFFF00"));
        }
        else if(t.getPriority().equals("High")){
            myViewHolder.priorityViewColor.setBackgroundColor(Color.parseColor("#32CD32"));
        }
        myViewHolder.taskStatus.setText(t.getStatus());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskDescription,taskTitle,taskAssignee,taskPriority,taskStatus;
        View priorityViewColor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDescription = itemView.findViewById(R.id.my_task_description);
            taskTitle = itemView.findViewById(R.id.my_task_title);
            taskAssignee = itemView.findViewById(R.id.my_task_assign_member);
            taskPriority = itemView.findViewById(R.id.my_task_priority);
            taskStatus = itemView.findViewById(R.id.my_task_status);
            priorityViewColor = itemView.findViewById(R.id.my_task_priority_view);
        }

    }
}
