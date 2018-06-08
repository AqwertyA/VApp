package com.v.minesweeper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author V
 * @since 2018/6/7
 */
class MineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)

        val mineView = findViewById<MineView>(R.id.mine_view)

        mineView.init(10, 10, 10)
    }
}