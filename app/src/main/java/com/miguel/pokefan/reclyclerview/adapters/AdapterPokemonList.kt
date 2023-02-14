package com.miguel.pokefan.reclyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miguel.pokefan.R
import com.miguel.pokefan.reclyclerview.pokemon

class AdapterPokemonList(private var items_pokemon: MutableList<pokemon> = ArrayList()):
    RecyclerView.Adapter<AdapterPokemonList.ViewHolder>(), OnClickListener{
    //implementacion del evento click del recyclerview en kotlin
    lateinit var listener: OnClickListener
    private var posicion: Int = 0
    fun setOnclickListener(listener:OnClickListener){
        this.listener = listener
    }

    fun  getPosicion(): Int {
        return posicion
    }

    fun setPosicion(posicion:Int){
        this.posicion = posicion
    }

    override fun onClick(v: View?) {
        if (listener!=null){
            listener.onClick(v)
        }
    }

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
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_pokemons, parent, false)
        vista.setOnClickListener(this)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.textViewID.text = items_pokemon[position].pokemonId
       val urlIMG = items_pokemon[position].urlPokemon
       Glide.with(holder.imgPokemon.context).load(urlIMG).error(R.drawable.icon_flecha_derecha).into(holder.imgPokemon)
       holder.textViewName.text = items_pokemon[position].pokemonName
    }

    override fun getItemCount(): Int {
        return  items_pokemon.size
    }



}



