package com.example.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * @author V
 * @since 2018/10/22
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> loadAll();

    @Query("SELECT * FROM user WHERE uid in (:userIds)")
    List<User> loadAllByIds(int... userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE (:name)")
    List<User> loadByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user WHERE uid in (:id)")
    void deleteById(int... id);
}

