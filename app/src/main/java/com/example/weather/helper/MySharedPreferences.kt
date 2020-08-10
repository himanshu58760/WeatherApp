package com.example.news.Helper

import com.example.weather.helper.Constants

object MySharedPreferences {

    private val sharedPreferences by lazy {
        MyApplication.getContext().getSharedPreferences(
            Constants.DEFAULT_SHARED_PREFS,
            Constants.DEFAAULT_MODE_SHARED_PREFS
        )
    }

    fun getUnits(): String {
        return sharedPreferences.getString(Constants.KEY_STRING, Constants.CELSIUS)!!
    }

    fun setUnits(unit: String) {
        sharedPreferences.edit().putString(Constants.KEY_STRING, unit).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(Constants.KEY_USER_NAME, "")!!
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString(Constants.KEY_USER_NAME, userName).apply()
    }
}