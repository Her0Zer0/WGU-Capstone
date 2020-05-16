package com.slyfoxdevelopment.example2.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "account")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String userName;
    private String passwd;
    private String firstname;
    private String lastname;
    private String salt;
    private Date created;
    private boolean keepMeLoggedIn;

    @Ignore
    public Account() {}
    @Ignore
    public Account(@NonNull String userName, String passwd, String firstname, String lastname, String salt, Date created, boolean keepMeLoggedIn) {
        this.userName = userName;
        this.passwd = passwd;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salt = salt;
        this.created = created;
        this.keepMeLoggedIn = keepMeLoggedIn;
    }


    public Account(int id, @NonNull String userName, String passwd, String firstname, String lastname, String salt, Date created, boolean keepMeLoggedIn) {
        this.id = id;
        this.userName = userName;
        this.passwd = passwd;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salt = salt;
        this.created = created;
        this.keepMeLoggedIn = keepMeLoggedIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isKeepMeLoggedIn() {
        return keepMeLoggedIn;
    }

    public void setKeepMeLoggedIn(boolean keepMeLoggedIn) {
        this.keepMeLoggedIn = keepMeLoggedIn;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", salt='" + salt + '\'' +
                ", created=" + created +
                ", keepMeLoggedIn=" + keepMeLoggedIn +
                '}';
    }
}
