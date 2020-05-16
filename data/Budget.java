package com.slyfoxdevelopment.example2.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "budget")
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String amount;
    private String type;
    private String label_title;
    private String label_color; //should be "" if not an string integer
    private String category;
    private Date created_date;
    private String notes;
    private int account_id;

    @Ignore
    public Budget() {}
    @Ignore
    public Budget(@NonNull String amount, String type, String label_title, String label_color, String category, Date created_date, /*String recurring_date, */String notes, int account_id) {
        this.amount = amount;
        this.type = type;
        this.label_title = label_title;
        this.label_color = label_color;
        this.category = category;
        this.created_date = created_date;
        this.notes = notes;
        this.account_id = account_id;
    }

    public Budget(int id, @NonNull String amount, String type, String label_title, String label_color, String category, Date created_date, /*String recurring_date,*/ String notes, int account_id) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.label_title = label_title;
        this.label_color = label_color;
        this.category = category;
        this.created_date = created_date;
        this.notes = notes;
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                ", label_title='" + label_title + '\'' +
                ", label_color='" + label_color + '\'' +
                ", created_date=" + created_date +
                ", notes='" + notes + '\'' +
                ", account_id=" + account_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel_title() {
        return label_title;
    }

    public void setLabel_title(String label_title) {
        this.label_title = label_title;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getLabel_color() {
        return label_color;
    }

    public void setLabel_color(String label_color) {
        this.label_color = label_color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
