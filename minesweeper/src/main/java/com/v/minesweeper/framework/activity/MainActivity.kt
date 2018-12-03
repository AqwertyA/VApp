package com.v.minesweeper.framework.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.v.minesweeper.R

/**
 * 游戏主界面
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun startGame(v: View) {
        startActivity(Intent(this, MineActivity::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    fun settings(v: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
