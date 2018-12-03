package com.v.minesweeper.framework.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.v.minesweeper.constant.STATE_MARK
import com.v.minesweeper.constant.STATE_SEARCH

/**
 * 游戏界面ViewModel
 * @author V
 * @since 2018/6/11
 */
class MineModel : ViewModel() {
    val state: ObservableInt = ObservableInt(STATE_SEARCH)

    /**
     * 改变当前模式  搜索或标记
     */
    fun changeState() {
        state.set(if (state.get() == STATE_SEARCH) STATE_MARK else STATE_SEARCH)
    }

    /**
     * 获取显示模式的文案
     */
    fun getSearchState(mode: Int) = when (mode) {
        STATE_SEARCH -> "search"
        STATE_MARK -> "mark"
        else -> "unknown"
    }
}