package com.slyfoxdevelopment.example2.ui.records;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Budget;

import java.util.List;

public class BudgetListViewModel extends AndroidViewModel {

    private AppRepository mRepo;

    public LiveData< List< Budget > > mLiveDataBudgetList;


    public BudgetListViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mLiveDataBudgetList = mRepo.getAllBudgets();
    }
}
