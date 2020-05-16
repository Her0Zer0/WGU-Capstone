package com.slyfoxdevelopment.example2.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Budget;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.text.Normalizer;
import java.util.List;

public class DashboardViewModel extends AndroidViewModel {


    AppRepository mRepo;

    public LiveData< List< Budget > > mLiveDataExpenses;
    public LiveData<List<Budget>> mLiveDataIncomes;
    Account mAccount = FormUtils.getLoggedInUserAccount();

    public DashboardViewModel(Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mLiveDataExpenses = mRepo.getAllExpenseByAccountId(mAccount.getId());
        mLiveDataIncomes = mRepo.getAllIncomeByAccountId(mAccount.getId());
    }


}