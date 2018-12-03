package com.v.minesweeper.framework.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.v.minesweeper.data.Difficulty

import com.v.minesweeper.data.Level

/**
 * 设置页面模型
 * @author V
 * @since 2018/11/29
 */
class SettingsModel : ViewModel() {

    var w = ObservableField<String>()//宽
    var h = ObservableField<String>()//高
    var n = ObservableField<String>()//雷数
    var difficulty = ObservableInt()//难度

    fun generateModel(): Level {
        return if (difficulty.get() == Difficulty.CUSTOM)
            Level(Integer.parseInt(w.get()), Integer.parseInt(h.get()), Integer.parseInt(n.get()))
        else Level(difficulty.get())
    }

    fun setDataFromModel(level: Level) {
        w.set(level.w.toString())
        h.set(level.h.toString())
        n.set(level.n.toString())
        difficulty.set(level.level)
    }
}
