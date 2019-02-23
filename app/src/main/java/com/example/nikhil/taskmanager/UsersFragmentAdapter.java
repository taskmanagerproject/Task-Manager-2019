package com.example.nikhil.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.taskmanager.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class UsersFragmentAdapter extends FirebaseRecyclerAdapter<Users, UsersFragmentAdapter.UsersViewHolder> {

    private List<Users> mData;
    private Context mContext;


    public UsersFragmentAdapter(@NonNull FirebaseRecyclerOptions<Users> options, Context context, List<Users> data) {
        super(options);
        this.mData = data;
        this.mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

        model  =mData.get(position);
        holder.mFullname.setText(model.getFullName());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_recy_view_items,viewGroup, false);
        return new UsersViewHolder(view);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView mFullname;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mFullname = itemView.findViewById(R.id.usersName);
        }
    }
}



