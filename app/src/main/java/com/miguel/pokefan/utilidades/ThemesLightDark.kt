package com.miguel.pokefan.utilidades

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ThemesLightDark {

    /**
     * Guarda el tema de la app en SharedPreferents
     * @param tema -> valor entero que determina el tema
     * @param context -> contexto de la aplicación
     * */
    @SuppressLint("CommitPrefEdits")
    fun GuardarTema(tema :Int, context: Context ){
        val sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Tema", tema.toString())
        editor.apply()
    }

    /**
     * Guarda el tema de la app en SharedPreferents
     * @param tema -> valor entero que determina el tema
     * @param context -> contexto de la aplicación
     * @return sharedPreferences -> devuelve el valor almacenado en el SharedPreferences
     * */
    fun MostrarTema (tema: Int, context: Context): String? {
        val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("Tema", null)
    }
}