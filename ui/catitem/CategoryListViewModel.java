package com.slyfoxdevelopment.example2.ui.catitem;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Categories;

import java.util.List;

public class CategoryListViewModel extends AndroidViewModel {

    public LiveData< List< Categories > > mLiveDataCategoriesList;

    private AppRepository mRepo;

    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(application.getApplicationContext());
        mLiveDataCategoriesList = mRepo.getAllCategories();
    }
}
