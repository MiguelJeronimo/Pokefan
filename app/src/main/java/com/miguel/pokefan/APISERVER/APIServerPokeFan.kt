package com.miguel.pokefan.APISERVER

import com.miguel.pokefan.APISERVER.Models.Pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

//aplicaremos corrutinas en la llamada a estas funciones en retrofit
interface APIServerPokeFan {
    /**
     * Recibe el nombre de las querys de la url:
     * offset y limit -> pokemon/?offset=20&limit=20
     * */
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("offset") min:String,
        @Query("limit") max:String
    ): Response<Pokemon>
}