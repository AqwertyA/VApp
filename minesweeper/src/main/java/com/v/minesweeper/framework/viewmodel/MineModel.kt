package com.v.minesweeper.framework.viewmodel

import android.databinding.ObservableInt
import com.v.minesweeper.constant.STATE_MARK
import com.v.minesweeper.constant.STATE_SEARCH

/**
 * @author V
 * @since 2018/6/11
 */
class MineModel {
    val state: ObservableInt = ObservableInt(STATE_SEARCH)

    fun changeState() {
        state.set(if (state.get() == STATE_SEARCH) STATE_MARK else STATE_SEARCH)
    }

    fun getSearchState(mode: Int) = when (mode) {
        STATE_SEARCH -> "search"
        STATE_MARK -> "mark"
        else -> "unknown"
    }
}