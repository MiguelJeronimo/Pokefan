package com.miguel.pokefan.utilidades

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstanciarRetrofit {
    /**
     * @param url -> url del endpoint a consumir
     * @return Regresa una instancia de retrofit
     * */
    fun getRetrofit(url: String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}