package com.miguel.pokefan.utilidades

import android.text.Editable
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList
import com.miguel.pokefan.reclyclerview.pokemon

class Busquedas {
    val items_pokemon: MutableList<pokemon> = ArrayList()
    suspend fun BuscarPokemon(pokemon: String?,  txtPokemon: TextInputEditText, layout: TextInputLayout){
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        val call =  retrofit
            .getRetrofit(urlPokemon)
            .create(APIServerPokeFan::class.java)
            .getPokemon(pokemon.toString())
        if (call.isSuccessful){
            val cuerpo = call.body()
            layout.error = null
            val imagenPokemonGo = cuerpo?.sprites?.other?.home!!.front_default
            val img: String
            val nombre = cuerpo.name
            val id = cuerpo.id.toString()
            if(imagenPokemonGo != null){
                img = imagenPokemonGo
            }else{
                img = cuerpo.sprites.other.official_artwork.front_default
            }
            items_pokemon.add(
                pokemon(
                    img,
                    nombre,
                    id
            ))
        }else{
            layout.error = "El pokemón no existe"
        }
    }
    fun ArrayAdapterPokemon(): MutableList<pokemon> {
        return items_pokemon
    }
}