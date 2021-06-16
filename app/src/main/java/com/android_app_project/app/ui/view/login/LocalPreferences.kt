package com.android_app_project.app.ui.view.login

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences private constructor(context: Context) {
    //Variable dans laquel on va stocker un String
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
    //Sauvegarde de la valeur
    fun saveStringValue(yourValue: String?) {
        sharedPreferences.edit().putString("saveStringValue", yourValue).apply()
    }
    //Accesseur de la valeur
    fun getSaveStringValue(): String? {
        return sharedPreferences.getString("saveStringValue", null)
    }

    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }

}