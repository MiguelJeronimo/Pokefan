package com.miguel.pokefan.APISERVER.Models

import com.google.gson.annotations.SerializedName

//Data class para extraer la imagen alternativa del pokemon
data class PokemonImgSprite(var sprite: Sprites)
data class Sprites(var other:Other)
data class Other(@SerializedName("official-artwork") var official_artwork: OfficialArtwork)
data class OfficialArtwork(var front_default: String)