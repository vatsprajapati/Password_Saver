package com.bce.passwordsaver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String title;
    private String userid;
    private String password;
    private String description;

    public Item(String title, String userid, String password, String description) {
        this.title = title;
        this.userid = userid;
        this.password = password;
        this.description = description;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }
}