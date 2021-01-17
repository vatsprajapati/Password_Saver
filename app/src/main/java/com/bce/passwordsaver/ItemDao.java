package com.bce.passwordsaver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);



    @Query("DELETE FROM item_table")
    void deleteAllItems();

    @Query("SELECT * FROM item_table ORDER BY id DESC")
    LiveData<List<Item>> getAllItems();
}
