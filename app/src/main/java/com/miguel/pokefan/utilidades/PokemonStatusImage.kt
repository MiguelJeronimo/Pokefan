package com.miguel.pokefan.utilidades

class PokemonStatusImage() {
    var pokemonURL:String = ""
    fun setPokemonUrl(pokemonURL: String){
        this.pokemonURL = pokemonURL
    }
    fun getPokemonUrl():String{
        return pokemonURL
    }
}