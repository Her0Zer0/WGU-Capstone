package com.slyfoxdevelopment.example2.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "label")
public class Label {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private String color;
    private int account_id;

    @Ignore
    public Label() {}
    @Ignore
    public Label(@NonNull String title, String color, int account_id) {
        this.title = title;
        this.color = color;
        this.account_id = account_id;
    }

    public Label(int id, @NonNull String title, String color, int account_id) {
        this.id = id;
        this.title = title;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
