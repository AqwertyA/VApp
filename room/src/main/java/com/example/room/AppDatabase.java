package com.example.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @author V
 * @since 2018/10/22
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "testDatabase";

    public abstract UserDao userDao();

}
