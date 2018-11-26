package com.example.room

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val users = ArrayList<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        database = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()

        adapter = UserAdapter(users, this)
        lv_users.adapter = adapter
        btn_refresh.setOnClickListener {
            DatabaseTask.TaskBuilder.newBuilder<Unit, Unit>().doInBackground { _ ->
                database.userDao().loadAll()
            }.onCompleteListener { _ ->
                this.users.clear()
                this.users.addAll(users)
                adapter.notifyDataSetChanged()
            }.build().execute()
        }
        fab.setOnClickListener {
            OperationActivity.start(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
