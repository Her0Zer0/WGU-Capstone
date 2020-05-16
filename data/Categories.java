package com.slyfoxdevelopment.example2.data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private int account_id;


    @Ignore
    public Categories() {
    }
    @Ignore
    public Categories(@NonNull String title, int account_id) {
        this.title = title;
        this.account_id = account_id;
    }

    public Categories(int id, @NonNull String title, int account_id) {
        this.id = id;
        this.title = title;
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
