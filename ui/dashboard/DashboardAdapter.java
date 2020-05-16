package com.slyfoxdevelopment.example2.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.ui.records.AddBudgetRecord;
import com.slyfoxdevelopment.example2.ui.records.BudgetAdapter;
import com.slyfoxdevelopment.example2.utils.Converters;

import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.BUDGET_ID_KEY;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private final List< Budget > mBudgets;
    private final Context mContext;

    public DashboardAdapter(List< Budget > mBudgets, Context mContext) {
        this.mBudgets = mBudgets;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.budget_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Budget budget = mBudgets.get(position);

        if(budget != null){
            holder.budget_list_amount.setText("$" +budget.getAmount());
            holder.budget_list_category.setText(budget.getCategory());
            holder.budget_list_date.setText( Converters.convertDateForView( budget.getCreated_date() ) );
            holder.budget_list_label.setText(budget.getLabel_title());
            holder.budget_list_type.setText(budget.getType());
            if(budget.getLabel_color().length() > 0){
                holder.budget_list_label.setBackgroundColor(Integer.parseInt( budget.getLabel_color() ) );
            }
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
        TextView budget_list_amount, budget_list_type, budget_list_label, budget_list_category, budget_list_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            budget_list_amount = itemView.findViewById(R.id.budget_list_amount);
            budget_list_type = itemView.findViewById(R.id.budget_list_type);
            budget_list_label = itemView.findViewById(R.id.budget_list_label);
            budget_list_category = itemView.findViewById(R.id.budget_list_category);
            budget_list_date = itemView.findViewById(R.id.budget_list_date);
        }
    }
}
