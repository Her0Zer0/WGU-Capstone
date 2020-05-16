package com.slyfoxdevelopment.example2.ui.catitem;

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
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryList extends AppCompatActivity {

    RecyclerView mRecycler;
    CategoryAdapter mAdpater;
    CategoryListViewModel mViewModel;
    List< Categories > mCategoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Categories");
        mRecycler = findViewById(R.id.category_list_recycler);

        initRecyclerView();

        initViewModel();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryEdit.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViewModel() {
        final Observer<List<Categories>> categoryObserver = new Observer< List< Categories > >() {
            @Override
            public void onChanged(List< Categories > categories) {
                mCategoryList.clear();
                Account mAccount = FormUtils.getLoggedInUserAccount();

                for(Categories cat : categories){
                    if(cat.getAccount_id() == mAccount.getId()){
                        mCategoryList.add(cat);
                    }
                }

                if(mAdpater == null){
                    mAdpater = new CategoryAdapter(mCategoryList, CategoryList.this);
                    mRecycler.setAdapter(mAdpater);
                }else{
                    mAdpater.notifyDataSetChanged();
                }


            }
        };

        mViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        mViewModel.mLiveDataCategoriesList.observe(this, categoryObserver);

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
