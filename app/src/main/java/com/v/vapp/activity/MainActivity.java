package com.v.vapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.v.vapp.DataHolder;
import com.v.vapp.R;
import com.v.vapp.databinding.ActivityMainBinding;
import com.v.vapp.viewModel.MainVM;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityUtils.finishAllActivities();
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(new MainVM(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataHolder.getInstance().logout();
    }
}
