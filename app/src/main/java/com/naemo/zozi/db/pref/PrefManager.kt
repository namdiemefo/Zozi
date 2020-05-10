package com.naemo.zozi.db.pref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefManager(context: Context) {

    private val SHARED_PREF_KEY_NAME = "shared_pref"
    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }


   /* private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor*/

    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_NAME = "configuration"
        private const val FIRST_TIME = "isFirstRun"
    }
    /*init {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        editor = preferences.edit()
        editor.apply()
    }*/

    fun setFirstRun() {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.putBoolean(FIRST_TIME, false)
        editor?.apply()
    }
    fun isFirstRun(): Boolean {
        val preferences = context?.getSharedPreferences(SHARED_PREF_KEY_NAME, Context.MODE_PRIVATE)
        if (preferences != null) {
            return preferences.getBoolean(FIRST_TIME, true)
        } else {
            return true
        }
    }



}