package com.v.animtest;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.v.animtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void startAnim(View view) {
        Drawable drawable = binding.ivAnim.getDrawable();
        if (drawable instanceof AnimatedVectorDrawable) {
            ((AnimatedVectorDrawable) drawable).start();
        }
    }
}
