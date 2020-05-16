package com.slyfoxdevelopment.example2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(Budget budget);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllBudgets(List<Budget> budgetList);


    @Query("SELECT * FROM budget ORDER BY id DESC")
    LiveData<List<Budget>> getAllBudgets();

    @Query("DELETE FROM budget")
    void deleteAllBudgets();

    @Query("SELECT * FROM budget WHERE id = :budget_id")
    Budget getBudgetById(int budget_id);

    @Query("SELECT * FROM budget WHERE account_id = :id ORDER BY id DESC LIMIT 5 ")
    LiveData< List< Budget>> getLastFiveBudgets(int id);

    @Query("SELECT * FROM budget WHERE account_id = :id AND type = 'Expense'")
    LiveData< List< Budget>> getAllExpenseByAccountId(int id);

    @Query("SELECT * FROM budget WHERE account_id = :id AND type = 'Income'")
    LiveData< List< Budget>> getAllIncomeByAccountId(int id);

    @Query("SELECT * FROM budget WHERE account_id = :id")
    LiveData< List< Budget>> getAllBudgetsByAccountId(int id);

    @Query("SELECT * FROM budget WHERE account_id = :id")
    List< Budget> getAllBudgetsListByAccountId(int id);

    @Query("DELETE FROM budget WHERE id = :id")
    void deleteBudgetById(int id);

    @Query("SELECT COUNT(*) FROM budget WHERE account_id = :id")
    int getCount(int id);
}
