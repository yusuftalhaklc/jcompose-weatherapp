package com.yusuftalhaklc.weatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class AppPref(var context:Context) {
    val Context.ds :DataStore<Preferences> by preferencesDataStore("weatherapp")

    companion object{
        val COLOR_KEY = intPreferencesKey("COLOR")
    }

    suspend fun setColor(color:Int){
        context.ds.edit {
            it[COLOR_KEY] = color
        }
    }
    suspend fun getColor():Int? {
        val p = context.ds.data.first()
        return p[COLOR_KEY]
    }

}