package com.slyfoxdevelopment.example2.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.data.SampleData;
import com.slyfoxdevelopment.example2.ui.catitem.CategoryList;
import com.slyfoxdevelopment.example2.ui.login.HomeScreen;
import com.slyfoxdevelopment.example2.ui.records.AddBudgetRecord;
import com.slyfoxdevelopment.example2.ui.catitem.CategoryEdit;
import com.slyfoxdevelopment.example2.ui.labels.LabelEdit;
import com.slyfoxdevelopment.example2.ui.labels.LabelList;
import com.slyfoxdevelopment.example2.ui.records.BudgetList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;

    private LatestBudgetAdapter budgetAdapter;
    private LatestLabelAdapter labelAdapter;
    private LatestCatAdapter catAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        List< Budget > mBudgetList = new ArrayList<>();
        List< Label > mLabelList = new ArrayList<>();
        List< Categories > mCategoiesList = new ArrayList<>();


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);





        RecyclerView budgetRecycler = (RecyclerView) root.findViewById(R.id.home_budget_list);
        budgetRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        RecyclerView labelRecycler = (RecyclerView) root.findViewById(R.id.home_label_recycler_view);
        labelRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView catRecycler = (RecyclerView) root.findViewById(R.id.home_category_recycler_view);
        catRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        final Observer< List<Budget> > budgetObserver = new Observer< List< Budget > >() {
            @Override
            public void onChanged(List< Budget > budgets) {
                mBudgetList.clear();
                mBudgetList.addAll(budgets);
                if(budgetAdapter == null){
                    budgetAdapter = new LatestBudgetAdapter(mBudgetList, getActivity());
                    budgetRecycler.setAdapter(budgetAdapter);
                    budgetRecycler.setItemAnimator(new DefaultItemAnimator());
                }else{
                    budgetAdapter.notifyDataSetChanged();
                }
            }
        };


        final Observer<List<Label>> labelObserver = new Observer< List< Label > >() {
            @Override
            public void onChanged(List< Label > labels) {
                mLabelList.clear();
                mLabelList.addAll(labels);

                if(labelAdapter == null){
                    labelAdapter = new LatestLabelAdapter(mLabelList, getActivity());
                    labelRecycler.setAdapter(labelAdapter);
                    labelRecycler.setItemAnimator(new DefaultItemAnimator());
                }else{
                    labelAdapter.notifyDataSetChanged();
                }
            }
        };


        final Observer<List<Categories>> catObserver = new Observer< List< Categories > >() {
            @Override
            public void onChanged(List< Categories > categories) {
                mCategoiesList.clear();
                mCategoiesList.addAll(categories);

                for(Categories cat : categories){
                    Log.i(TAG, "onChanged: " + cat.getTitle());
                }

                if(catAdapter == null){
                    catAdapter = new LatestCatAdapter(mCategoiesList, getActivity());
                    catRecycler.setAdapter(catAdapter);
                    catRecycler.setItemAnimator(new DefaultItemAnimator());
                }else{
                    catAdapter.notifyDataSetChanged();
                }
            }
        };



        homeViewModel.mLiveDataBudgetList.observe(getActivity(), budgetObserver);
        homeViewModel.mLiveDataLabelList.observe(getActivity(), labelObserver);
        homeViewModel.mLiveDataCategoriesList.observe(getActivity(), catObserver);



        //TODO: BUILD OUT RECORDS LIST AND SET EDIT RECORDS TO DISPLAY DATA IF OLD RECORD IS CLICKED
        //TODO: SETUP RECYCLER VIEWS FOR THE HOME PAGE
        //TODO: SETUP CHARTS AND GRAPHS FOR THE
        //budget records
        final ImageButton home_add_record_btn = root.findViewById(R.id.home_add_record_btn);
        final ImageButton home_list_record_btn = root.findViewById(R.id.home_list_record_btn);

        home_add_record_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddBudgetRecord.class);
            startActivity(intent);
            
        });

        home_list_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BudgetList.class);
                startActivity(intent);

            }
        });

        //label records
        final ImageButton home_add_label_btn = root.findViewById(R.id.home_add_label_btn);
        final ImageButton home_list_label_btn = root.findViewById(R.id.home_list_label_btn);

        home_add_label_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LabelEdit.class);
                startActivity(intent);

            }
        });

        home_list_label_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LabelList.class);
                startActivity(intent);

            }
        });

        //categories records
        final ImageButton home_add_category_btn = root.findViewById(R.id.home_add_category_btn);
        final ImageButton home_list_category_btn = root.findViewById(R.id.home_list_category_btn);

        home_add_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryEdit.class);
                startActivity(intent);

            }
        });

        home_list_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryList.class);
                startActivity(intent);

            }
        });



//        Log.i(TAG, "onCreateView: " + SampleData.getDate(-5));


        return root;
    }
}