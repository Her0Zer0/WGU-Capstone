package com.slyfoxdevelopment.example2.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterViewModel extends AndroidViewModel {

    private AppRepository mRepo;
    public MutableLiveData<Account> mLiveAccount = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();


    public RegisterViewModel(@NonNull Application application) {
        super(application);

        mRepo = AppRepository.getInstance(getApplication());
    }


    public void insertAccount(Account account) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepo.insertAccount(account);
            }
        });

    }


}
