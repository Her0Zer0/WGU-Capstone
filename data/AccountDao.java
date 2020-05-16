package com.slyfoxdevelopment.example2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAccounts(List< Account > accounts);

    @Query("DELETE FROM account WHERE id = :id")
    void deleteAccountById(int id);

    @Query("DELETE FROM account")
    void deleteAllAccounts();

    @Query("SELECT * FROM account WHERE id = :id")
    Account getAccountById(int id);

    @Query("SELECT * FROM account WHERE id = :username AND id = :passwd")
    Account getAccountByUserPasswd(String username, String passwd);

    @Query("SELECT * FROM account")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT COUNT(*) FROM account")
    int getAccountCount();

    @Query("UPDATE account SET keepMeLoggedIn = :keepMeLoggedIn WHERE id = :id")
    void updateKeepMeLoggedIn(int id, Boolean keepMeLoggedIn);
    @Query("SELECT * FROM account WHERE keepMeLoggedIn = 1") //1 = true 0= false
    Account isKeepMeLoggedIn();

    @Query("UPDATE account SET keepMeLoggedIn = 0")
    void logEveryoneOut();

    @Query("UPDATE account SET keepMeLoggedIn = 0 WHERE id = :id")
    void logOutUserById(int id);
}
