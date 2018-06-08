package com.v.vapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.v.vapp.R;
import com.v.vapp.databinding.ActivityLoginBinding;
import com.v.vapp.viewModel.LoginVM;

/**
 * 登录页面
 *
 * @author V
 * @since 2018/5/25
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setModel(new LoginVM(this));
    }
}
