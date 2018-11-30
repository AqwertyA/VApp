package com.v.minesweeper.constant

/**
 * 常量定义
 * @author V
 * @since 2018/6/7
 */
/**Mine Item的显示状态*/
const val MINE_STATE_REAL_MINE = -4  //真的雷
const val MINE_STATE_FLAG_UNKNOWN = -3 //？标识
const val MINE_STATE_FLAG_MINE = -2  //雷标识
const val MINE_STATE_DEFAULT = -1      //默认状态
//周围有0~8个雷状态
const val MINE_STATE_NUM_0 = 0
const val MINE_STATE_NUM_1 = 1
const val MINE_STATE_NUM_2 = 2
const val MINE_STATE_NUM_3 = 3
const val MINE_STATE_NUM_4 = 4
const val MINE_STATE_NUM_5 = 5
const val MINE_STATE_NUM_6 = 6
const val MINE_STATE_NUM_7 = 7
const val MINE_STATE_NUM_8 = 8

/**搜索状态*/
const val STATE_SEARCH = 0//搜索状态
const val STATE_MARK = 1//标记状态

/**获取设置preference的key*/
const val PREF_KEY_SETTINGS = "settings"
/**传递难度的model的Key*/
const val PREF_KEY_SETTINGS_LV = "settings_lv"