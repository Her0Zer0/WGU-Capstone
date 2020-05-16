package com.slyfoxdevelopment.example2.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.example2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;
    private AppDatabase mdb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData< List<Account> > mAccounts;
    public LiveData<List<Budget>> mBudgets;
    public LiveData<List<Label>> mLabels;
    public List<Categories> mCategoriesList = new ArrayList<>();
//    public List<Label> mLabelList = new ArrayList<>();

    public static AppRepository getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return  ourInstance;
    }



    private AppRepository(Context context){
        mdb = AppDatabase.getInstance(context);
        mAccounts = getAllAccounts();
        mBudgets = getAllBudgets();
        mLabels = getAllLabels();
        this.getListOfCategories();
    }


    // Categories Class Methods

    /**
     * repository.getAllCategories()
     * @return List of all category records from the category table
     */
    public LiveData< List< Categories>> getAllCategories() {
        return mdb.categoriesDao().getAllCategories();
    }


    private void getListOfCategories() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Categories> mCategories = new ArrayList<>();
                mCategories.addAll(mdb.categoriesDao().getListOfCategories());
                mCategoriesList.addAll(mCategories);

            }
        });

    }


    // Label Class Methods

    /**
     * repository.getAllLabels()
     * @return List of all label records from the label table
     */
    public LiveData< List< Label>> getAllLabels() {
        return mdb.labelDao().getAllLabels();
    }


    public List< Label>  getListOfLabels(int id) {
        return mdb.labelDao().getListOfLabels(id);
    }





    // Budgets Class Methods

    /**
     * repository.getAllBudgets()
     * @return List of all budget records from the budget table
     */
    public LiveData< List< Budget>> getAllBudgets() {
        return mdb.budgetDao().getAllBudgets();
    }




    //Accounts Class Methods


    /**
     * repository.getAllAccounts()
     * @return List of all user account records from the user table
     */
    public LiveData< List< Account>> getAllAccounts() {
        return mdb.accountDao().getAllAccounts();
    }

    /**
     * repository.insertAccount(account_object);
     * @param account -> Account object to be recorded in the accounts table
     */
    public void insertAccount(Account account){
        executor.execute(() -> mdb.accountDao().insertAccount(account));
    }

    public Account getAccountByUserPasswd(String user, String passwd) {
        return mdb.accountDao().getAccountByUserPasswd(user, passwd);
    }

    public int getAccountCount() {
        return mdb.accountDao().getAccountCount();
    }

    public void updateUser(int id, Boolean keepMeLoggedIn) {
        mdb.accountDao().updateKeepMeLoggedIn(id, keepMeLoggedIn);
    }

    public Account isKeepMeLoggedIn() {
        return  mdb.accountDao().isKeepMeLoggedIn();
    }

    public void logEveryoneOut() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.accountDao().logEveryoneOut();
            }
        });

    }

    public void insertLabel(Label label) {
        executor.execute(() -> mdb.labelDao().insertLabel(label));
    }

    public void insertCategory(Categories category) {

        executor.execute(() -> mdb.categoriesDao().insertCategory(category));
    }


    public Label loadLabel(int label_id) {
        return mdb.labelDao().getLabelById(label_id);
    }

    public Categories getCategoryById(int cat_id) {
        return mdb.categoriesDao().getCategoryById(cat_id);
    }

    public void insertBudget(Budget mBudget) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.budgetDao().insertBudget(mBudget);
            }
        });
    }

    public Budget loadBudget(int budget_id) {
        return mdb.budgetDao().getBudgetById(budget_id);
    }

    public LiveData< List< Budget>> getLastFiveBudgets(int id) {
        return mdb.budgetDao().getLastFiveBudgets(id);
    }

    public LiveData< List< Label>> getLastFiveLabels(int id) {
        return mdb.labelDao().getLast5Labels(id);
    }

    public LiveData< List< Categories>> getLastFiveCategories(int id) {
        return mdb.categoriesDao().getLast5Categories(id);
    }

    public LiveData< List< Budget>> getAllExpenseByAccountId(int id) {
        return mdb.budgetDao().getAllExpenseByAccountId(id);
    }

    public LiveData< List< Budget>> getAllIncomeByAccountId(int id) {
        return mdb.budgetDao().getAllIncomeByAccountId(id);
    }

    public LiveData< List< Budget>> getAllBudgetsByAccountId(int id) {
        return mdb.budgetDao().getAllBudgetsByAccountId(id);
    }

    public void deleteLabelById(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.labelDao().deleteLabelById(id);
            }
        });
    }

    public void deleteBudgetById(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.budgetDao().deleteBudgetById(id);
            }
        });
    }

    public void deleteCatItemById(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.categoriesDao().deleteCategoryById(id);
            }
        });
    }

    public void logOutUser(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mdb.accountDao().logOutUserById(id);
            }
        });
    }

    public LiveData< List< Label>> getAllLabelsByAccountId(int id) {
               return mdb.labelDao().getAllLabelsByAccountId(id);
    }

    public Collection<? extends Categories> getCategoryListByAccountId(int id) {
        return mdb.categoriesDao().getCategoryListByAccountId(id);
    }
}
