package com.slyfoxdevelopment.example2.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;

@Dao
public interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Categories category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategories(List<Categories> categories);

    @Query("SELECT * FROM categories")
    LiveData< List<Categories> > getAllCategories();

    @Query("DELETE FROM categories")
    void deleteCategories();

    @Query("SELECT * FROM categories")
    List<Categories> getListOfCategories();

    @Query("SELECT * FROM categories WHERE id = :cat_id")
    Categories getCategoryById(int cat_id);

    @Query("SELECT * FROM categories WHERE account_id = :id ORDER BY id DESC LIMIT 5")
    LiveData< List< Categories>> getLast5Categories(int id);

    @Query("DELETE FROM categories WHERE id = :id")
    void deleteCategoryById(int id);

    @Query("SELECT * FROM categories WHERE account_id = :id")
    List<Categories> getCategoryListByAccountId(int id);

    @Query("SELECT COUNT(*) FROM categories WHERE account_id = :id")
    int getCategoryCountById(int id);
}
