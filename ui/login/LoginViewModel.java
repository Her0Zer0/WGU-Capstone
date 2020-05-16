package com.slyfoxdevelopment.example2.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel {

    private AppRepository mRepo;
    public LiveData< List< Account > > mLiveAccount;

    private Executor executor = Executors.newSingleThreadExecutor();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(getApplication());
        mLiveAccount = mRepo.getAllAccounts();

    }

    public void updateUser(int id, Boolean keepMeLoggedIn) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepo.updateUser(id, keepMeLoggedIn);
            }
        });
    }

    public void logEveryoneOut() {
        mRepo.logEveryoneOut();
    }
}
