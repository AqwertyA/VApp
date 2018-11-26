package com.example.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * 用户数据模型
 *
 * @author V
 * @since 2018/10/22
 */
@Entity(tableName = "user")
public class User {
    public static final int GENDER_NONE = 0;
    public static final int GENDER_F = 1;
    public static final int GENDER_M = 2;
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "gender")
    private int gender;

    public User() {
    }

    @Ignore
    public User(int uid, String userName, int gender) {
        this.uid = uid;
        this.userName = userName;
        this.gender = gender;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        return getGenderString(gender);
    }

    public static String getGenderString(int gender) {
        return gender == GENDER_F ? "f" : gender == GENDER_M ? "m" : "";
    }

    public static int getGenderInt(String gender) {
        return "f".equals(gender) ? GENDER_F : "m".equals(gender) ? GENDER_M : GENDER_NONE;
    }
}
