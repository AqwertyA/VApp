package com.v.minesweeper.data

import com.google.gson.Gson
import java.io.Serializable

/**
 * 难度类
 * @author V
 * @since 2018/11/29
 */
class Level : Serializable {

    var level: Int = Difficulty.EASY//默认是简单难度
    var w: Int = 10
    var h: Int = 10
    var n: Int = 10

    constructor(difficulty: Int) {
        this.level = difficulty
        when (difficulty) {
            Difficulty.EASY -> {
                w = 10
                h = 10
                n = 10
            }
            Difficulty.MEDIUM -> {
                w = 20
                h = 20
                n = 40
            }
            Difficulty.HARD -> {
                w = 20
                h = 20
                n = 60
            }
            Difficulty.EXPERT -> {
                w = 30
                h = 20
                n = 100
            }
        }
    }

    constructor(w: Int, h: Int, n: Int) {
        this.level = Difficulty.CUSTOM
        this.w = w
        this.h = h
        this.n = n
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
