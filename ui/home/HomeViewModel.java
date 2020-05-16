package com.slyfoxdevelopment.example2.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    public LiveData< List< Budget > > mLiveDataBudgetList;
    public LiveData<List< Label >> mLiveDataLabelList;
    public LiveData<List< Categories >> mLiveDataCategoriesList;
    public LiveData<List<Budget>> mLiveDateAllBudgets;

    private AppRepository mRepo;
    private Account mAccount = FormUtils.getLoggedInUserAccount();
    Executor executor = Executors.newSingleThreadExecutor();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mLiveDataBudgetList = mRepo.getLastFiveBudgets(mAccount.getId());
        mLiveDataLabelList = mRepo.getLastFiveLabels(mAccount.getId());
        mLiveDataCategoriesList = mRepo.getLastFiveCategories(mAccount.getId());
        mLiveDateAllBudgets = mRepo.getAllBudgetsByAccountId(mAccount.getId());
    }

    public void logOutUser() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepo.logOutUser(mAccount.getId());
            }
        });
    }
}