package com.v.minesweeper.framework.activity

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.google.gson.Gson
import com.v.minesweeper.R
import com.v.minesweeper.constant.PREF_KEY_SETTINGS
import com.v.minesweeper.constant.PREF_KEY_SETTINGS_LV
import com.v.minesweeper.data.Difficulty
import com.v.minesweeper.data.Level
import com.v.minesweeper.databinding.ActivitySettingsBinding
import com.v.minesweeper.framework.adapter.SpinnerAdapter
import com.v.minesweeper.framework.viewmodel.SettingsModel

/**
 * @author V
 * @since 2018/11/28
 */
class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var dataBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        dataBinding.model = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(SettingsModel::class.java)
        val lvString = getSharedPreferences(PREF_KEY_SETTINGS, Context.MODE_PRIVATE).getString(PREF_KEY_SETTINGS_LV, null)
        val level = if (lvString == null) Level(Difficulty.EASY) else Gson().fromJson(lvString, Level::class.java)
        dataBinding.model!!.setDataFromModel(level)

        dataBinding.spinner.adapter = SpinnerAdapter(this)
        dataBinding.spinner.setSelection(level.level)
        dataBinding.spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        dataBinding.model!!.setDataFromModel(if (position != Difficulty.CUSTOM) Level(position) else Level(Difficulty.CUSTOM))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        save()
    }

    private fun save() {
        getSharedPreferences(PREF_KEY_SETTINGS, Context.MODE_PRIVATE).edit().putString(PREF_KEY_SETTINGS_LV, dataBinding.model!!.generateModel().toString()).apply()
    }

}