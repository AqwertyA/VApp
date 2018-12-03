package com.v.minesweeper.framework.activity

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.gson.Gson
import com.v.minesweeper.R
import com.v.minesweeper.constant.PREF_KEY_SETTINGS
import com.v.minesweeper.constant.PREF_KEY_SETTINGS_LV
import com.v.minesweeper.data.Difficulty
import com.v.minesweeper.data.Level
import com.v.minesweeper.databinding.ActivityMineBinding
import com.v.minesweeper.framework.viewmodel.MineModel

/**
 * 游戏界面
 * @author V
 * @since 2018/11/26
 */
class MineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMineBinding
    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mine)

        //获取通过json数据保存的难度
        val preferences = getSharedPreferences(PREF_KEY_SETTINGS, Context.MODE_PRIVATE)
        val lvString = preferences.getString(PREF_KEY_SETTINGS_LV, null)
        level = if (lvString == null) Level(Difficulty.EASY) else Gson().fromJson(lvString, Level::class.java)

        binding.model = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MineModel::class.java)
        play()
    }

    @Suppress("UNUSED_PARAMETER")
    fun play(v: View) {
        play()
    }

    private fun play() {
        binding.mineView.play(level.w, level.h, level.n)
    }
}