package com.example.nikhil.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikhil.taskmanager.model.Tasks;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    /*@NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }*/
    protected Context mContext;
    private List<Tasks> mData;
    MyAdapter(Context context,List<Tasks> data){
        mContext=context;
        mData=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.all_task_recy_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Tasks P=mData.get(position);
        /*holder.textView1.setText(P.getName());
        holder.textView3.setText(""+P.getQty());*/
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1,textView2,textView3;
        Button minusb1,plusb2;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.task_title);
            textView2=itemView.findViewById(R.id.task_description);
        }
    }
}
