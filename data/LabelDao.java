package com.slyfoxdevelopment.example2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLabel(Label label);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllLabel(List<Label> labelList);

    @Query("SELECT * FROM label")
    LiveData<List<Label>> getAllLabels();

    @Query("DELETE FROM label")
    void deleteAllLabel();

    @Query("SELECT * FROM label WHERE account_id = :id")
    List< Label> getListOfLabels(int id);

    @Query("SELECT * FROM label WHERE id = :label_id")
    Label getLabelById(int label_id);

    @Query("SELECT * FROM label WHERE account_id = :id ORDER BY id DESC LIMIT 5")
    LiveData< List< Label>> getLast5Labels(int id);

    @Query("DELETE FROM label WHERE id = :id")
    void deleteLabelById(int id);

    @Query("SELECT * FROM label WHERE account_id = :id")
    LiveData<List<Label>> getAllLabelsByAccountId(int id);

    @Query("SELECT COUNT(*) FROM label WHERE account_id= :id")
    int getLabelCountById(int id);
}
