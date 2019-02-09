package com.example.nikhil.taskmanager;

import android.content.Context;
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
        Tasks t = mData.get(i);
        myViewHolder.taskTitle.setText(t.getTitle());
        myViewHolder.taskDescription.setText(t.getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskDescription,taskTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskTitle = itemView.findViewById(R.id.task_title);
        }

    }
}
