package com.example.nikhil.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhil.taskmanager.model.Tasks;
import com.example.nikhil.taskmanager.task.view.TaskDataActivity;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder>  {

    protected  Context mContext;
    private List<Tasks> mData;
    public AllTaskAdapter(Context context, List<Tasks> data){
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_task_recy_item,viewGroup, false);
        return new AllTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Tasks t = mData.get(i);
        Log.d(TAG,"Values are "+t.getTitle());
        Log.d(TAG,"Values are "+t.getDescription());
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
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,t.getID(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,TaskDataActivity.class);
                intent.putExtra("TASK_ID",t.getID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskDescription,taskTitle,taskAssignee,taskPriority,taskStatus;
        View priorityViewColor;
        MaterialCardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskAssignee = itemView.findViewById(R.id.task_assign_member);
            taskPriority = itemView.findViewById(R.id.task_priority);
            priorityViewColor = itemView.findViewById(R.id.task_priority_view);
            taskStatus = itemView.findViewById(R.id.task_status);
            cardView = itemView.findViewById(R.id.allTaskCardview);
        }

    }
}
