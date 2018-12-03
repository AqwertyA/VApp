package com.v.minesweeper.framework.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.v.minesweeper.R

/**
 * 难度选择适配器
 * @author V
 * @since 2018/11/29
 */
class SpinnerAdapter(private var context: Context) : BaseAdapter() {
    private var array = context.resources.getTextArray(R.array.spinner_difficulty_lv)

    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): Any? {
        return array[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val itemView: TextView = if (convertView == null) {
            //使用系统自带的简单TextView布局
            View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null).findViewById(android.R.id.text1)
        } else {
            convertView.findViewById(android.R.id.text1)
        }
        itemView.text = array[position]
        return itemView
    }
}
