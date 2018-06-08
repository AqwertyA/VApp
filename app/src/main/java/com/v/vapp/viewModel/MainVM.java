package com.v.vapp.viewModel;

import android.app.AlertDialog;
import android.databinding.ObservableField;

import com.v.vapp.DataHolder;
import com.v.vapp.activity.MainActivity;

/**
 * @author V
 * @since 2018/5/27
 */

public class MainVM {
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    public ObservableField<String> key = new ObservableField<>();
    private MainActivity activity;

    public MainVM(MainActivity activity) {
        this.activity = activity;
        userName.set(DataHolder.getInstance().getCurrentUserName());
    }

    public void check() {
        String key = this.key.get();
        String userPwd = DataHolder.getInstance().getUserPwd(key);
        pwd.set(userPwd);
        if (userPwd != null) {
            new AlertDialog.Builder(activity)
                    .setTitle("your key-->" + key)
                    .setMessage("your pwd-->" + userPwd)
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            new AlertDialog.Builder(activity)
                    .setMessage("find no pwd matched with key " + key)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    public void save() {
        String key = this.key.get();
        String pwd = this.pwd.get();
        DataHolder.getInstance().saveUserPwd(key, pwd);
        new AlertDialog.Builder(activity)
                .setMessage("pwd save success\nkey-->" + key + "\npwd-->" + pwd)
                .setPositiveButton("OK", null)
                .show();
    }

}
