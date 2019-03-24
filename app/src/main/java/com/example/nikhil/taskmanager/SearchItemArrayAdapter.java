package com.example.nikhil.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.nikhil.taskmanager.model.Users;

import java.util.ArrayList;
import java.util.List;

public class SearchItemArrayAdapter extends ArrayAdapter<Users> {

    protected Context mContext;
    private ArrayList<Users> mData,mSuggestionList;
    ConstraintLayout mAutoConsLayout;
    private Users userstEntry;
    private TextView autoItem,fullNameTv;
    private ImageView usersIcon;
    private List<Users> usersEntryList = new ArrayList<Users>();
    private List<Users> mCopyDataList;

    private static final String TAG = "SearchItemArrayAdapter";
    int mResource;
    ColorGenerator generator;

    public SearchItemArrayAdapter(Context context, int resource, List<Users> data) {
        super(context, resource, data);
        mContext = context;
        mData = (ArrayList<Users>) data;
        mCopyDataList = (ArrayList<Users>)mData.clone();
        mSuggestionList = new ArrayList<Users>();
        mResource = resource;
        this.generator= ColorGenerator.MATERIAL;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public Users getItem(int position) {
        Users u = this.mData.get(position);
        return u;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return usersDataFilter;
    }

    Filter usersDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence!=null){
                mSuggestionList.clear();
                for (Users users:mCopyDataList){
                    if (users.getFullName().toLowerCase().startsWith(charSequence.toString().toLowerCase())){
                        mSuggestionList.add(users);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mSuggestionList;
                filterResults.count = mSuggestionList.size();
                Log.d(TAG,"Data are "+mData.size());
                Log.d(TAG,"Size of filter results "+filterResults.count);
                return  filterResults;

            }
            else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Users> filteredList = (ArrayList<Users>) filterResults.values;
            if (filterResults!=null && filterResults.count > 0){
                clear();
                for (Users u:filteredList){
                    add(u);
                }
                notifyDataSetChanged();
            }
        }
    };


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.w(TAG, "GetView");

        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (row == null)
        {
            row = inflater.inflate(R.layout.activity_autocomplete_layout, parent, false);
        }

        userstEntry = mData.get(position);
        String firstLetter = userstEntry.getFullName().substring(0,1).toUpperCase();
        Log.d(TAG,"Email are: "+userstEntry.getFullName());
        String searchItem = userstEntry.getEmail();
        TextDrawable drawable=TextDrawable.builder().buildRound(firstLetter, generator.getColor(searchItem));
        mAutoConsLayout = row.findViewById(R.id.autoCompleteConsLayout);

        fullNameTv = row.findViewById(R.id.usersFullName);
        fullNameTv.setText(userstEntry.getFullName());
        autoItem =  row.findViewById(R.id.usersName);
        autoItem.setText(searchItem);
        usersIcon =  row.findViewById(R.id.usersImageAutoTV);
        usersIcon.setImageDrawable(drawable);



        return row;
    }
}
