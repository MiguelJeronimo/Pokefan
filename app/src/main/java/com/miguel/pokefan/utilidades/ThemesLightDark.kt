package com.miguel.pokefan.utilidades

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ThemesLightDark {

    @SuppressLint("CommitPrefEdits")
    fun guardarTema(tema :Int, context: Context ){
        val sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Tema", tema.toString())
        editor.apply()
    }

    fun MostrarTema (tema: Int, context: Context): String? {
        val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("Tema", null)
    }
}