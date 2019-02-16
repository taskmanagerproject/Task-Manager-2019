package com.example.nikhil.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhil.taskmanager.model.Users;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    protected Context mContext;
    private List<Users> mData;
    public UsersAdapter(Context context, List<Users> data){
        this.mContext = context;
        this.mData = data;
        Log.d(TAG,"Data is"+mData);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_recy_view_items,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Users u = mData.get(i);
        Log.d(TAG,"Value position "+i);
        Log.d(TAG,"Full Name is "+u.getFullName());
        myViewHolder.mName.setText(u.getFullName());
        myViewHolder.mEmail.setText(u.getEmail());
        /*int clickedPosition=myViewHolder.getAdapterPosition();
        notifyItemChanged(clickedPosition);*/
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mEmail,mName;
        ConstraintLayout mConstraintLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.usersName);
            mEmail=itemView.findViewById(R.id.usersEmail);
            mConstraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }

}
