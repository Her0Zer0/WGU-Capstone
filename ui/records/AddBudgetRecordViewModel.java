package com.slyfoxdevelopment.example2.ui.records;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddBudgetRecordViewModel extends AndroidViewModel {

    public LiveData< List< Categories>> mLiveCategoriesList;
    private AppRepository mRepo;
    public List<Label>  mLabelsList = new ArrayList<>();
    public List<Categories> mCategoriesList = new ArrayList<>();
    public LiveData<List<Label>> mLiveLabelList;

    public MutableLiveData<Budget> mBudgetMutableLiveData = new MutableLiveData<>();

    Executor executor = Executors.newSingleThreadExecutor();


    Account mAccount = FormUtils.getLoggedInUserAccount();
    public AddBudgetRecordViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());

        this.getLabelListAccountId(mAccount.getId());
        this.getCategoryListByAccountId(mAccount.getId());
        mLiveLabelList = mRepo.getAllLabelsByAccountId(mAccount.getId());
        mLiveCategoriesList = mRepo.getAllCategories();

    }

    private void getCategoryListByAccountId(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoriesList.addAll(mRepo.getCategoryListByAccountId(id));
            }
        });
    }

    private void getLabelListAccountId(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                mLabelsList.addAll(mRepo.getListOfLabels(id));
            }
        });



    }


    private List< Categories> getCategoryList() {
        return mRepo.mCategoriesList;
    }



    public void saveBudget(Budget budget) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Budget mBudget = mBudgetMutableLiveData.getValue();
                Account loggedInUser = FormUtils.getLoggedInUserAccount();
                if(mBudget != null){
                    mBudget.setAccount_id(loggedInUser.getId());
                    mBudget.setType(budget.getType());
                    mBudget.setLabel_title(budget.getLabel_title());
                    mBudget.setLabel_color(budget.getLabel_color());
                    mBudget.setCategory(budget.getCategory());
                    mBudget.setCreated_date(budget.getCreated_date());
                    mBudget.setNotes(budget.getNotes());
                    mBudget.setAmount(budget.getAmount());
                    mRepo.insertBudget(mBudget);
                }else{
                    mRepo.insertBudget(budget);
                }
            }
        });
    }

    public void loadRecord(int budget_id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Budget budget = mRepo.loadBudget(budget_id);
                mBudgetMutableLiveData.postValue(budget);
            }
        });
    }

    public void deleteCurrentBudget() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Budget budget = mBudgetMutableLiveData.getValue();
                mRepo.deleteBudgetById(budget.getId());
            }
        });
    }
}
