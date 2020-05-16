package com.slyfoxdevelopment.example2.ui.labels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Label;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.text.Normalizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LabelEditViewModel extends AndroidViewModel {

    private AppRepository mRepo;

    public MutableLiveData<Label> mLabelMutableLiveData = new MutableLiveData<>();

    private Executor executor = Executors.newSingleThreadExecutor();

    public LabelEditViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(getApplication());

    }

    public void loadLabel(final int label_id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Label label = mRepo.loadLabel(label_id);
                mLabelMutableLiveData.postValue(label);
            }
        });
    }


    public void insertLabel(Label label) {
        executor.execute(() -> {
            Label mLabel = mLabelMutableLiveData.getValue();
            Account mAccount = FormUtils.getLoggedInUserAccount();
            if(mLabel != null){
                mLabel.setTitle(label.getTitle());
                mLabel.setColor(label.getColor());
                mLabel.setAccount_id(mAccount.getId());
                mRepo.insertLabel(mLabel);
            }else{
                mRepo.insertLabel(label);
            }
        });
    }

    public void deleteCurrentLabel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Label mlabel = mLabelMutableLiveData.getValue();
                mRepo.deleteLabelById(mlabel.getId());
            }
        });


    }
}
