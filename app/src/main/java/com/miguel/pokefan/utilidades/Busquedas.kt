package com.miguel.pokefan.utilidades

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.APISERVER.Models.PokemonImgSprite
import com.miguel.pokefan.databinding.FragmentSecondBinding
import com.miguel.pokefan.reclyclerview.pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class Busquedas {
    val items_pokemon: MutableList<pokemon> = ArrayList()
    val retrofit = InstanciarRetrofit()
    val urlPokemon = "https://pokeapi.co/api/v2/"
     fun BuscarPokemon(pokemon: String?,  txtPokemon: TextInputEditText, layout: TextInputLayout){
        val apiServerPokeFan =  retrofit.getRetrofit(urlPokemon).create(APIServerPokeFan::class.java)
        val call = apiServerPokeFan.getPokemon(pokemon.toString())
        call.enqueue(object: Callback<PokemonImgSprite>{
            override fun onResponse(call: Call<PokemonImgSprite>, response: Response<PokemonImgSprite>, ) {
              if(response.isSuccessful){
                  val cuerpo = response.body()
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
              } else{
                  layout.error = "El pokemón no existe"
              }
            }

            override fun onFailure(call: Call<PokemonImgSprite>, t: Throwable) {

            }
        })
    }
    fun ArrayAdapterPokemon(): MutableList<pokemon> {
        return items_pokemon
    }

     fun Pokedex(pokemon:String, binding: FragmentSecondBinding, context: Context){
         val apiServerPokeFan =  retrofit.getRetrofit(urlPokemon)
             .create(APIServerPokeFan::class.java)
         val call = apiServerPokeFan.getPokemon(pokemon)
         val hp = binding.textViewHP
         val attack = binding.textViewAttack
         val defense = binding.textViewDefense
         val specialAttack = binding.textViewSpecialAtt
         val specialDefense = binding.textViewSpecialDef
         val speed = binding.textViewSpeed
         val imagePokemon = binding.imageViewPokemon
         call.enqueue(object :Callback<PokemonImgSprite>{
             override fun onResponse(call: Call<PokemonImgSprite>, response: Response<PokemonImgSprite>, ) {
                 if (response.isSuccessful){
                     val cuerpo = response.body()
                     val id = cuerpo?.id
                     val imagenPokemonGo = cuerpo?.sprites?.other?.home!!.front_default
                     binding.cardPokedex.visibility = View.VISIBLE
                     Glide.with(context).load(ValidarUrlPokemon(imagenPokemonGo, id!!)).into(imagePokemon);
                     binding.textViewPokemon.text = cuerpo.name
                     hp.text = "HP: ${cuerpo.stats[0].base_stat}"
                     attack.text = "Attack: ${cuerpo.stats[1].base_stat}"
                     defense.text = "Defense: ${cuerpo.stats[2].base_stat}"
                     specialAttack.text = "Special Attack: ${cuerpo.stats[3].base_stat}"
                     specialDefense.text = "Special Defense: ${cuerpo.stats[4].base_stat}"
                     speed.text = "Speed: ${cuerpo.stats[5].base_stat}"
                 }
             }

             override fun onFailure(call: Call<PokemonImgSprite>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
             }
         })
     }

    fun ValidarUrlPokemon(url:String?, id: Int): String {
        if (url!=null){
           return  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png"
        } else{
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
        }
    }
}