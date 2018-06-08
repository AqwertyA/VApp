package com.v.vapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

/**
 * @author V
 * @since 2018/5/25
 */
public class DataHolder {
    private static DataHolder instance;

    private DataHolder() {
    }

    private SharedPreferences accountPref;//账户信息
    private SharedPreferences userPref;//用户信息
    private String currentUserName;//当前已登录的用户名兼ID

    public synchronized static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public boolean login(String userName, String pwd) {
        boolean success = checkPwd(userName, pwd);
        if (success) {
            userPref = Utils.getApp().getApplicationContext().getSharedPreferences(userName, Context.MODE_PRIVATE);
            currentUserName = userName;
        } else
            ToastUtils.showShort(R.string.loginFailed);
        return success;
    }

    /**
     * get  password
     */
    public String getUserPwd(String type) {
        String pwdEncode = userPref.getString(type, null);
        if (pwdEncode == null) {
            return null;
        }
        return new String(Base64.decode(pwdEncode, Base64.NO_WRAP));
    }

    /**
     * save password
     */
    public void saveUserPwd(String type, String pwd) {
        userPref.edit().putString(type, Base64.encodeToString(pwd.getBytes(), Base64.NO_WRAP)).apply();
    }

    public boolean register(String userName, String pwd) {
        checkAccountPref();
        if (accountPref.getString(userName, null) != null || userName.equals(Constant.PrefKey.ACCOUNT_INFO)) {
            LogUtil.w("log failed, user already registered!");
            return false;
        } else {
            accountPref.edit().putString(userName, Base64.encodeToString(pwd.getBytes(), Base64.NO_WRAP)).apply();
            return login(userName, pwd);
        }
    }

    /**
     * @return true if the pwd is true
     */
    public boolean checkPwd(String userName, String pwd) {
        checkAccountPref();
        String pwdEncode = accountPref.getString(userName, null);
        return !(pwdEncode == null || !pwdEncode.equals(Base64.encodeToString(pwd.getBytes(), Base64.NO_WRAP)));
    }

    private void checkAccountPref() {
        if (accountPref == null) {
            accountPref = Utils.getApp().getSharedPreferences(Constant.PrefKey.ACCOUNT_INFO, Context.MODE_PRIVATE);
        }
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void logout() {
        userPref = null;
        currentUserName = null;
    }
}
