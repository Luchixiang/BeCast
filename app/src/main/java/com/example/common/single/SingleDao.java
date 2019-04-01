package com.example.common.single;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface
SingleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addHistory(Single single);
    @Query("SELECT*FROM singleHistory")
    List<Single> getAllHistory();
    @Delete
    void deleteHistory(Single single);
    @Query("SELECT*FROM singleHistory")
    List<Single> getAllList();
}
