package com.example.room

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * @author V
 * @since 2018/10/22
 */
class SpinnerAdapter(private val context: Context) : BaseAdapter() {
    private val operations = DatabaseOperation.values()

    override fun getCount(): Int {
        return operations.size
    }

    override fun getItem(position: Int): Any? {
        return operations[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val itemView: TextView = if (convertView == null) {
            View.inflate(context, R.layout.item_spinner, null).findViewById(R.id.tv_option)
        } else {
            convertView.findViewById(R.id.tv_option)
        }
        itemView.text = operations[position].name
        return itemView
    }
}
