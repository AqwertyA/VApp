package com.v.vapp.viewModel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.v.vapp.DataHolder;
import com.v.vapp.R;
import com.v.vapp.activity.LoginActivity;
import com.v.vapp.activity.MainActivity;

/**
 * @author V
 * @since 2018/5/25
 */
public class LoginVM {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();

    private LoginActivity activity;

    public LoginVM(LoginActivity activity) {
        this.activity = activity;
    }

    public void login() {
        if (!checkInput()) {
            return;
        }
        if (!DataHolder.getInstance().login(name.get(), pwd.get())) {
            ToastUtils.showShort(R.string.pwdIncorrectOrUserNotExist);
        } else {
            activity.startActivity(new Intent(activity, MainActivity.class));
        }
    }

    public void register() {
        if (!checkInput()) {
            return;
        }
        if (DataHolder.getInstance().register(name.get(), pwd.get())) {
            activity.startActivity(new Intent(activity, MainActivity.class));
        } else {
            ToastUtils.showShort(String.format(Utils.getApp().getString(R.string.userAlreadyRegistered), name.get()));
        }
    }

    /**
     * 检查帐号密码输入规则
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(name.get())) {
            ToastUtils.showShort(R.string.userNameEmpty);
            return false;
        }
        if (TextUtils.isEmpty(pwd.get())) {
            ToastUtils.showShort(R.string.pwdEmpty);
            return false;
        }
        if (pwd.get().length() < 6) {
            ToastUtils.showShort(R.string.pwdTooShort);
            return false;
        }
        return true;
    }
}
