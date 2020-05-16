package com.slyfoxdevelopment.example2.ui.records;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.utils.Converters;

import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.BUDGET_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.BUDGET_IS_NEW;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {


    private final List< Budget > mBudgets;
    private final Context mContext;

    public BudgetAdapter(List< Budget > mBudgets, Context mContext) {
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

            holder.budget_list_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.getApplicationContext(), AddBudgetRecord.class);
                    intent.putExtra(BUDGET_ID_KEY, budget.getId());
                    intent.putExtra(BUDGET_IS_NEW, false);
                    mContext.startActivity(intent);
                    ( (Activity) view.getContext() ).finish();
                }
            });

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

        CardView budget_list_card_view;
        TextView budget_list_amount, budget_list_type, budget_list_label, budget_list_category, budget_list_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            budget_list_card_view = itemView.findViewById(R.id.budget_list_card_view);
            budget_list_amount = itemView.findViewById(R.id.budget_list_amount);
            budget_list_type = itemView.findViewById(R.id.budget_list_type);
            budget_list_label = itemView.findViewById(R.id.budget_list_label);
            budget_list_category = itemView.findViewById(R.id.budget_list_category);
            budget_list_date = itemView.findViewById(R.id.budget_list_date);
        }
    }
}
