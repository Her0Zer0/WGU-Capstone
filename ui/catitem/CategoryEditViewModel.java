package com.slyfoxdevelopment.example2.ui.catitem;

import android.app.Application;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.example2.data.Account;
import com.slyfoxdevelopment.example2.data.AppRepository;
import com.slyfoxdevelopment.example2.data.Categories;
import com.slyfoxdevelopment.example2.utils.FormUtils;

import java.text.Normalizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryEditViewModel extends AndroidViewModel {

    AppRepository mRepo;

    public MutableLiveData<Categories> mCategoriesMutableLiveData = new MutableLiveData<>();

    Executor executor = Executors.newSingleThreadExecutor();

    public CategoryEditViewModel(@NonNull Application application) {
        super(application);
        mRepo = AppRepository.getInstance(getApplication());
    }





    public void insertCategory(Categories category) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Categories mCat = mCategoriesMutableLiveData.getValue();
                Account mAccount = FormUtils.getLoggedInUserAccount();

                if(mCat != null){
                    mCat.setTitle(category.getTitle());
                    mCat.setAccount_id(mAccount.getId());
                    mRepo.insertCategory(mCat);
                }else{
                    mRepo.insertCategory(category);
                }
            }
        });

    }

    public void loadCategory(int cat_id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
               Categories cat =  mRepo.getCategoryById(cat_id);
               mCategoriesMutableLiveData.postValue(cat);
            }
        });
    }

    public void deleteCurrentCatItem() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Categories cat = mCategoriesMutableLiveData.getValue();
                mRepo.deleteCatItemById(cat.getId());
            }
        });
    }
}
