package com.karem.postly.data.cached;

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.karem.postly.data.cached.entity.LoggedInUser

class SettingsPref {
    private var sharedPreferences: SharedPreferences? = null

    fun initSettings(context: Context) {
        sharedPreferences = context.getSharedPreferences("SettingsPref", Context.MODE_PRIVATE)
    }

    private fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    fun getSettings(): Settings {
        if (sharedPreferences == null)
            throw Exception("Settings not been initialized yet...")

        val jsonSettings = gson().toJson(Settings())
        val settings = sharedPreferences?.getString("settings", jsonSettings)
        return gson().fromJson(settings, Settings::class.java)
    }

    fun updateSettings(update: (Settings) -> Unit) {
        if (sharedPreferences == null)
            throw Exception("Settings not been initialized yet...")

        var jsonSettings = gson().toJson(Settings())
        val settings = sharedPreferences?.getString("settings", jsonSettings)
        val settingsObject = gson().fromJson(settings, Settings::class.java)
        update(settingsObject)
        jsonSettings = gson().toJson(settingsObject)
        sharedPreferences!!.edit()!!.putString("settings", jsonSettings)!!.apply()
    }

    class Settings(
        var user: LoggedInUser? = null
    )

}
