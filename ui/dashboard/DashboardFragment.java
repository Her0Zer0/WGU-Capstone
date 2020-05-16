package com.slyfoxdevelopment.example2.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.utils.Converters;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    ImageButton filter_btn, refresh_btn, share_btn;
    TextView filtered_by;
    Spinner by_item_spinner, by_time_spinner;

    DashboardAdapter mAdapter;

    List<Budget> mExpenses = new ArrayList<>();
    List<String> mlabels = new ArrayList<>();
    List<String > mCategories = new ArrayList<>();
    List<Budget> filteredList = new ArrayList<>();
    String shareString = "";
    Account mAccount = FormUtils.getLoggedInUserAccount();
    public static final String TAG = "DashboardFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        filter_btn = root.findViewById(R.id.filter_btn);
        filtered_by = root.findViewById(R.id.filtered_by);
        by_item_spinner = root.findViewById(R.id.by_item_spinner);
        by_time_spinner = root.findViewById(R.id.by_time_spinner);
        refresh_btn = root.findViewById(R.id.refresh_btn);
        share_btn = root.findViewById(R.id.share_btn);

        RecyclerView budgetRecycler = (RecyclerView) root.findViewById(R.id.reporting_recycler);
        budgetRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));



        by_item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int i, long l) {

                    filtered_by.setText("");

            }

            @Override
            public void onNothingSelected(AdapterView< ? > adapterView) {

            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Budget Reports from " + mAccount.getFirstname() + " " + mAccount.getLastname());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                startActivity(Intent.createChooser(sharingIntent, "Options"));
            }
        });

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time_filter = by_time_spinner.getSelectedItem().toString();
                String item_filter = by_item_spinner.getSelectedItem().toString();
                String filter = filtered_by.getText().toString();
                filteredList.clear();

                shareString = "";

                //if by label

                if(item_filter.equalsIgnoreCase("label")){
                    //check for filter
                    if(!filter.isEmpty()){
                        for(Budget budget : mExpenses){

                            if(time_filter.equalsIgnoreCase("today")){
                                if(FormUtils.isAfterYesterday(-1).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getLabel_title())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this week")){

                                if(FormUtils.isAfterYesterday(-7).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getLabel_title())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }

                            }

                            if(time_filter.equalsIgnoreCase("this month")){

                                if(FormUtils.isAfterYesterday(-30).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getLabel_title())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this year")){

                                if(FormUtils.isAfterYesterday(-365).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getLabel_title())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                        }

                        mAdapter.notifyDataSetChanged();

                    }else{
                        for(Budget budget : mExpenses){


                            if(time_filter.equalsIgnoreCase("today")){
                                if(FormUtils.isAfterYesterday(-1).before(budget.getCreated_date()) && filter.equalsIgnoreCase("")){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this week")){

                                if(FormUtils.isAfterYesterday(-7).before(budget.getCreated_date()) && filter.equalsIgnoreCase("")){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }

                            }

                            if(time_filter.equalsIgnoreCase("this month")){

                                if(FormUtils.isAfterYesterday(-30).before(budget.getCreated_date()) && filter.equalsIgnoreCase("")){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this year")){

                                if(FormUtils.isAfterYesterday(-365).before(budget.getCreated_date()) && filter.equalsIgnoreCase("")){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }

                if(item_filter.equalsIgnoreCase("category")){
                    //check for filter
                    if(!filter.isEmpty()){

                        for(Budget budget : mExpenses){

                            if(time_filter.equalsIgnoreCase("today")){
                                if(FormUtils.isAfterYesterday(-1).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getCategory())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this week")){

                                if(FormUtils.isAfterYesterday(-7).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getCategory())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }

                            }

                            if(time_filter.equalsIgnoreCase("this month")){

                                if(FormUtils.isAfterYesterday(-30).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getCategory())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }

                            if(time_filter.equalsIgnoreCase("this year")){

                                if(FormUtils.isAfterYesterday(-365).before(budget.getCreated_date()) && filter.equalsIgnoreCase(budget.getCategory())){
                                    filteredList.add(budget);
                                    shareString += shareStringBuilder(budget);
                                }
                            }
                        }

                        mAdapter.notifyDataSetChanged();


                    }
                }

                if(item_filter.equalsIgnoreCase("all")){
                    for(Budget budget: mExpenses){

                        if(time_filter.equalsIgnoreCase("today")){
                            if(FormUtils.isAfterYesterday(-1).before(budget.getCreated_date()) ){
                                filteredList.add(budget);
                                shareString += shareStringBuilder(budget);
                            }
                        }

                        if(time_filter.equalsIgnoreCase("this week")){

                            if(FormUtils.isAfterYesterday(-7).before(budget.getCreated_date()) ){
                                filteredList.add(budget);
                                shareString += shareStringBuilder(budget);
                            }

                        }

                        if(time_filter.equalsIgnoreCase("this month")){

                            if(FormUtils.isAfterYesterday(-30).before(budget.getCreated_date()) ){
                                filteredList.add(budget);
                                shareString += shareStringBuilder(budget);
                            }
                        }

                        if(time_filter.equalsIgnoreCase("this year")){

                            if(FormUtils.isAfterYesterday(-365).before(budget.getCreated_date())){
                                filteredList.add(budget);
                                shareString += shareStringBuilder(budget);
                            }
                        }

                    }

                    mAdapter.notifyDataSetChanged();
                }


            }


        });

        final Observer< List< Budget > > expenseByCatObserver = new Observer< List< Budget > >() {
            @Override
            public void onChanged(List< Budget > budgets) {
                mExpenses.clear();
                mExpenses.addAll(budgets);
                filteredList.clear();
                filteredList.addAll(budgets);

                shareString = "";
                List<String> tempLabels = new ArrayList<>();
                List<String> tempCats = new ArrayList<>();

                for(Budget bud: budgets){
                    tempLabels.add(bud.getLabel_title());
                    tempCats.add(bud.getCategory());
                    shareString += shareStringBuilder(bud);
                }

                runAdapter(filteredList, budgetRecycler);

                mlabels = tempLabels.stream().distinct().collect(Collectors.toList());
                mCategories = tempCats.stream().distinct().collect(Collectors.toList());

            }
        };

        dashboardViewModel.mLiveDataExpenses.observe(getActivity(), expenseByCatObserver);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String item_filter = by_item_spinner.getSelectedItem().toString();

                if(item_filter.equalsIgnoreCase("all")){
                    return;
                }


                if(item_filter.equalsIgnoreCase("label")){
                    String[] items = mlabels.toArray(new String[0]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Pick a filter");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i(TAG, "onClick: " + i);
                            filtered_by.setText(items[i]);
                        }
                    });

                    builder.show();
                }else{
                    String[] items = mCategories.toArray(new String[0]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Pick a filter");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.i(TAG, "onClick: " + i);
                            filtered_by.setText(items[i]);
                        }
                    });

                    builder.show();
                }




            }
        });









        return root;
    }

    private String shareStringBuilder(Budget budget) {
        String str;
        str = "Amount: " + budget.getAmount() + "\nType: " + budget.getType() + "\nCreated: " + Converters.convertDateForView(budget.getCreated_date()) + "\nCategory: " + budget.getCategory() + "\nLabel: " + budget.getLabel_title() + "\nNotes: " + budget.getNotes()  + "\n\n";
        return str;
    }

    private void runAdapter(List<Budget> budgets, RecyclerView recycler) {
        if(mAdapter == null){
            mAdapter = new DashboardAdapter(budgets, getActivity());
            recycler.setAdapter(mAdapter);
            recycler.setItemAnimator(new DefaultItemAnimator());
        }
    }


}