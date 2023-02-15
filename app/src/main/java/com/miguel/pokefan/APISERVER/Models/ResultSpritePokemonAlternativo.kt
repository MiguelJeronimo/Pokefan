package com.miguel.pokefan.APISERVER.Models

import com.google.gson.annotations.SerializedName

//Data class para extraer la imagen alternativa del pokemon
data class PokemonImgSprite(val sprites: Sprites, val name:String, val id: Int, val stats: ArrayList<Stats>, val types: ArrayList<Types>)

data class Sprites(val other:Other)
data class Other(@SerializedName("official-artwork") val official_artwork: OfficialArtwork, val home: Home)
data class OfficialArtwork(val front_default: String, val front_shiny:String)
data class Home(val front_default: String, val front_shiny: String)
data class Stats(val base_stat: Int)
data class Types(val type: Type)
data class Type(val name: String)
