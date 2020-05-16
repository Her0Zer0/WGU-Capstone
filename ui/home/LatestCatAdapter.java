package com.slyfoxdevelopment.example2.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Categories;

import java.util.List;

public class LatestCatAdapter extends RecyclerView.Adapter<LatestCatAdapter.ViewHolder> {

    private final List< Categories > mCategories;
    private final Context mContext;

    public LatestCatAdapter(List< Categories > mCategories, Context mContext) {
        this.mCategories = mCategories;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categories cat = mCategories.get(position);
        if(cat != null){
            holder.default_text_1.setText(cat.getTitle());
            holder.default_text_2.setText("");
        }

    }

    @Override
    public int getItemCount() {
        if(mCategories != null){
            return mCategories.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView default_text_1, default_text_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            default_text_1 = itemView.findViewById(R.id.default_text_1);
            default_text_2 = itemView.findViewById(R.id.default_text_2);
        }
    }
}
