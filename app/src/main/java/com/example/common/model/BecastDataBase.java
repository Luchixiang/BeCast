package com.example.common.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.common.single.Single;
import com.example.common.single.SingleDao;

@Database(entities = {Single.class}, version = 1, exportSchema = false)
public abstract class BecastDataBase extends RoomDatabase {
    private static volatile BecastDataBase INSTANCE;

    public abstract SingleDao singleDao();

    public static BecastDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BecastDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BecastDataBase.class, "becast_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
