package com.project.speechtotext

import android.content.Context
import android.content.SharedPreferences


class DataPreference(context: Context) {

    private var prefs : SharedPreferences? = null
    private var isOnBoardingScreenShowed = "isOnBoardingScreenShowed"

    companion object {
        private var instance: DataPreference? = null
        @Synchronized
        fun getInstance(applicationContext: Context): DataPreference {
            if (instance == null) {
                instance = DataPreference(applicationContext)
            }
            return instance!!
        }
    }

    init {
        prefs = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    fun isOnBoardingScreenShowed(): Boolean {
        return prefs!!.getBoolean(isOnBoardingScreenShowed, false)
    }

    fun setOnBoardingScreenStatus(isOnBoardingScreenShowed: Boolean) {
        prefs!!.edit().putBoolean(this.isOnBoardingScreenShowed, isOnBoardingScreenShowed).apply()
    }

}