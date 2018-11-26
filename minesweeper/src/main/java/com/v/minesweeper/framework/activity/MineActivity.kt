package com.v.minesweeper.framework.activity

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.v.minesweeper.R
import com.v.minesweeper.databinding.ActivityMineBinding
import com.v.minesweeper.framework.viewmodel.MineModel

/**
 * @author V
 * @since 2018/11/26
 */
class MineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMineBinding>(this, R.layout.activity_mine)

        binding.mineView.play(10, 10, 10)
        binding.model = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MineModel::class.java)
    }
}