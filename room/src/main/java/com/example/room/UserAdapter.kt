package com.example.room

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class UserAdapter(private val users: ArrayList<User>, private val context: Context) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val holder: ViewHolder
        if (convertView == null) {
            itemView = View.inflate(context, R.layout.item_user, null)
            holder = ViewHolder(itemView)
            itemView.tag = holder
        } else {
            itemView = convertView
            holder = itemView.tag as ViewHolder
        }
        val user = users[position]
        holder.tvId.text = String.format(context.getString(R.string.idFormat), user.uid)
        holder.tvName.text = String.format(context.getString(R.string.nameFormat), user.userName)
        holder.tvGender.text = String.format(context.getString(R.string.genderFormat), user.genderString)
        return itemView
    }

    override fun getItem(position: Int): Any = users[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = users.size

    class ViewHolder(itemView: View) {
        val tvId = itemView.findViewById<TextView>(R.id.tv_id)!!
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)!!
        val tvGender = itemView.findViewById<TextView>(R.id.tv_gender)!!
    }
}
