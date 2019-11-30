package com.mili.workmanagerandpendingnotification

import android.content.Context
import android.preference.PreferenceManager
import androidx.annotation.NonNull

object SharedPrefHelpers {

    fun readFromSharedPreferences(context: Context, key: String, @NonNull defaultValue:String) : String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key,defaultValue)
    }

    fun writeToSharedPreferences(context: Context, key: String, @NonNull value:String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }
}