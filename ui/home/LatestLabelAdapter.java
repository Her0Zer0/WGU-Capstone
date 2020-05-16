package com.slyfoxdevelopment.example2.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.ui.records.BudgetAdapter;
import com.slyfoxdevelopment.example2.utils.Converters;

import java.util.List;

public class LatestLabelAdapter extends RecyclerView.Adapter<LatestLabelAdapter.ViewHolder> {


    private final List< Label > mLabels;
    private final Context mContext;

    public LatestLabelAdapter(List< Label > mLabels, Context mContext) {
        this.mLabels = mLabels;
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
        Label label = mLabels.get(position);
        if(label != null){
            holder.default_text_1.setText(label.getTitle());
            holder.default_text_2.setBackgroundColor( Integer.parseInt(label.getColor()));
            holder.default_text_2.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(mLabels != null){
            return mLabels.size();
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
