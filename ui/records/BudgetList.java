package com.slyfoxdevelopment.example2.ui.records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.ArrayList;
import java.util.List;

public class BudgetList extends AppCompatActivity {

    RecyclerView mRecycler;
    BudgetAdapter mAdapter;
    BudgetListViewModel mViewModel;

    List< Budget > mBudgetList = new ArrayList<>();
    Account loggedInUser = FormUtils.getLoggedInUserAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Records");

        mRecycler = findViewById(R.id.budget_list_recycler);
        initRecyclerView();

        initViewModel();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBudgetRecord.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViewModel() {
        final Observer<List<Budget>> budgetObserver = new Observer< List< Budget > >() {
            @Override
            public void onChanged(List< Budget > budgets) {
                mBudgetList.clear();

                for(Budget record : budgets){
                    if(record.getAccount_id() == loggedInUser.getId()){
                        mBudgetList.add(record);
                    }
                }

                if(mAdapter == null){
                    mAdapter = new BudgetAdapter(mBudgetList, BudgetList.this);
                    mRecycler.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }

            }
        };
        mViewModel = ViewModelProviders.of(this).get(BudgetListViewModel.class);
        mViewModel.mLiveDataBudgetList.observe(this, budgetObserver);
    }

    private void initRecyclerView() {
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }
}
