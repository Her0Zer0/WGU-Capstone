package com.slyfoxdevelopment.example2.ui.labels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.ArrayList;
import java.util.List;

public class LabelList extends AppCompatActivity {


    RecyclerView mRecycler;
    LabelAdapter mAdapter;
    LabelListViewModel mViewModel;
    List<Label> labelList = new ArrayList<>();
    Account loggedInUser = FormUtils.getLoggedInUserAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Labels");
        mRecycler = findViewById(R.id.label_list_recycler);

        initRecyclerView();

        initViewModel();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), LabelEdit.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void initViewModel() {

        final Observer< List< Label > > labelObserver = new Observer< List< Label > >() {
            @Override
            public void onChanged(List< Label > labels) {
                labelList.clear();

                for(Label label: labels){
                    if(label.getAccount_id() == loggedInUser.getId()){
                        labelList.add(label);
                    }
                }
//                labelList.addAll(labels);

                if(mAdapter == null){
                    mAdapter = new LabelAdapter(labelList, LabelList.this);
                    mRecycler.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(LabelListViewModel.class);
        mViewModel.mLiveDateLabelList.observe(this, labelObserver);

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
