package com.miguel.pokefan.APISERVER.Models

import com.google.gson.annotations.SerializedName

//Data class para extraer la imagen alternativa del pokemon
data class PokemonImgSprite(val sprites: Sprites, val name:String, val id: Int)

data class Sprites(val other:Other)
data class Other(@SerializedName("official-artwork") val official_artwork: OfficialArtwork, val home: Home)
data class OfficialArtwork(val front_default: String)
data class Home(val front_default: String)