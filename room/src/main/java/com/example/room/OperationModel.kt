package com.example.room

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Room
import android.content.Context
import android.databinding.ObservableField
import android.util.Log

/**
 * @author V
 * @since 2018/10/22
 */
class OperationModel(context: Context) : ViewModel() {

    val operation = ObservableField<DatabaseOperation>()
    val uid = ObservableField<String>()
    val userName = ObservableField<String>()
    val gender = ObservableField<String>()
    val adapter = SpinnerAdapter(context)
    private val database = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()

    fun execute() {
        when (operation.get()) {
            DatabaseOperation.INSERT -> {
                if (checkEmpty(true, true, true)) return
                val uid = Integer.parseInt(this.uid.get())
                val gender = User.getGenderInt(this.gender.get())
                val user = User(uid, userName.get(), gender)
                DatabaseTask.TaskBuilder.newBuilder<Void, Unit>().doInBackground { database.userDao().insertAll(user) }.build().execute()
            }
            DatabaseOperation.DELETE -> {
                if (checkEmpty(true, false, false)) return
                val uid = Integer.parseInt(this.uid.get())
                DatabaseTask.TaskBuilder.newBuilder<Void, Unit>().doInBackground { database.userDao().deleteById(uid) }.build().execute()
            }
            DatabaseOperation.LOAD_BY_ID -> {
                if (checkEmpty(true, false, false)) return
                val uid = Integer.parseInt(this.uid.get())

                DatabaseTask.TaskBuilder.newBuilder<Unit, User>().doInBackground { _ ->
                    database.userDao().loadAllByIds(uid).let { if (it.size >= 1) it[0] else null }
                }.onCompleteListener { user ->
                    if (user == null) {
                        Log.w("testTag", "no user loaded")
                        return@onCompleteListener
                    }
                    userName.set(user.userName)
                    gender.set(user.genderString)
                }.build().execute()
            }
            DatabaseOperation.LOAD_BY_NAME -> {
                if (checkEmpty(false, true, false)) return

                DatabaseTask<Void, User>({ _ ->
                    database.userDao().loadByName(userName.get()).let { if (it.size >= 1) it[0] else null }
                }, { user ->
                    if (user == null) {
                        Log.w("testTag", "no user loaded")
                        return@DatabaseTask
                    }
                    uid.set(user.uid.toString())
                    gender.set(user.genderString)
                }).execute()
            }
        }
    }

    private fun checkEmpty(checkId: Boolean, checkName: Boolean, checkGender: Boolean): Boolean {
        if (checkId && uid.get().isNullOrEmpty()) return true
        if (checkName && userName.get().isNullOrEmpty()) return true
        if (checkGender && gender.get().isNullOrEmpty()) return true
        return false
    }

}
