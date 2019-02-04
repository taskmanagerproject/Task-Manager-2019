package com.example.nikhil.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.taskmanager.model.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    protected Context mContext;
    private List<Users> mData;
    public UsersAdapter(Context context, List<Users> data){
        mContext = context;
        mData = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_recy_view_items,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Users u = mData.get(i);
        myViewHolder.textView1.setText(u.getFullName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.usersName);
        }
    }

}
