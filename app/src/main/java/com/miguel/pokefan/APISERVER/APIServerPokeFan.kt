package com.miguel.pokefan.APISERVER

import com.miguel.pokefan.APISERVER.Models.Pokemon
import retrofit2.Call
import retrofit2.http.GET

interface APIServerPokeFan {
    @GET("pokemon")
    fun getAllPokemon(): Call<Pokemon>
}