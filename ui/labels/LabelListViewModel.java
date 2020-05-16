package com.slyfoxdevelopment.example2.ui.labels;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Label;

import java.util.List;

public class LabelListViewModel extends AndroidViewModel {

    private AppRepository mRepo;
    public LiveData< List< Label > >mLiveDateLabelList;



    public LabelListViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mLiveDateLabelList = mRepo.getAllLabels();
    }




}
