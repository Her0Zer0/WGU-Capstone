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
import com.slyfoxdevelopment.example2.ui.records.BudgetAdapter;
import com.slyfoxdevelopment.example2.utils.Converters;

import java.util.List;

public class LatestBudgetAdapter extends RecyclerView.Adapter<LatestBudgetAdapter.ViewHolder> {

    private final List<Budget >  mBudgets;
    private final Context mContext;

    public LatestBudgetAdapter(List< Budget > mBudgets, Context mContext) {
        this.mBudgets = mBudgets;
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
        Budget budget = mBudgets.get(position);
        if(budget != null){
            holder.default_text_1.setText("Ammount: $" + budget.getAmount());
            holder.default_text_2.setText("Date: " + Converters.convertDateForView(budget.getCreated_date()));
        }

    }

    @Override
    public int getItemCount() {
        if(mBudgets != null){
            return mBudgets.size();
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
