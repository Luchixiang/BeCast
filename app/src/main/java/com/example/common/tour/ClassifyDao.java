//package com.example.common.tour;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;
//
//import java.util.List;
//@Dao
//public interface ClassifyDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertClass(Classify classify);
//    @Query("SELECT*FROM favorite_class")
//    List<Classify> getFavorite();
//    @Delete()
//    void deleteFavorite(Classify classify);
//}
