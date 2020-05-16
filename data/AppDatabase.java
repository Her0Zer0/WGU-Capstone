package com.slyfoxdevelopment.example2.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.slyfoxdevelopment.example2.utils.Converters;

import java.util.concurrent.Executors;

@Database(entities = {Account.class, Budget.class, Label.class, Categories.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Apps4.db";
    public static volatile AppDatabase instance;
    public static final Object LOCK = new Object();
    public abstract AccountDao accountDao();
    public abstract BudgetDao budgetDao();
    public abstract LabelDao labelDao();
    public abstract CategoriesDao categoriesDao();


    public static AppDatabase getInstance(Context context){
        if(instance == null){
                synchronized (LOCK){
                    if(instance == null){
                        instance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class,
                                DATABASE_NAME)
                                .addCallback(new Callback() {
                                    @Override
                                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                        super.onCreate(db);
                                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                getInstance(context).accountDao().insertAccount(SampleData.setAccount());
                                                getInstance(context).labelDao().insertAllLabel(SampleData.setLabels());
                                                getInstance(context).categoriesDao().insertAllCategories(SampleData.setCategories());
                                                getInstance(context).budgetDao().insertAllBudgets(SampleData.setBudgets());
                                            }
                                        });
                                    }
                                })
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
        }
        return instance;
    }



}
