package com.miguel.pokefan.APISERVER

import com.miguel.pokefan.APISERVER.Models.Pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
//aplicaremos corrutinas en la llamada a estas funciones en retrofit
interface APIServerPokeFan {
    @GET("pokemon")
    suspend fun getAllPokemon(): Response<Pokemon>
}