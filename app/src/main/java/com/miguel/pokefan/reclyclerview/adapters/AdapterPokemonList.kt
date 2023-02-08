package com.miguel.pokefan.reclyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miguel.pokefan.R
import com.miguel.pokefan.reclyclerview.pokemon

class AdapterPokemonList(private var items_pokemon: MutableList<pokemon> = ArrayList()):
    RecyclerView.Adapter<AdapterPokemonList.ViewHolder> (){

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var imgPokemon: ImageView
        var textViewName: TextView
        var textViewID: TextView
        init {
            imgPokemon = view.findViewById(R.id.imageViewPokemon)
            textViewName = view.findViewById(R.id.textViewName)
            textViewID = view.findViewById(R.id.textViewID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
        return ViewHolder(vista.inflate(R.layout.recyclerview_pokemons, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.textViewID.text = items_pokemon[position].pokemonId
       val urlIMG = items_pokemon[position].urlPokemon
       Glide.with(holder.imgPokemon.context).load(urlIMG).into(holder.imgPokemon)
       holder.textViewName.text = items_pokemon[position].pokemonName
    }

    override fun getItemCount(): Int {
        return  items_pokemon.size
    }

}



