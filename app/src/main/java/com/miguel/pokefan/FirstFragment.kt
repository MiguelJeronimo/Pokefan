package com.miguel.pokefan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.databinding.FragmentFirstBinding
import com.miguel.pokefan.reclyclerview.pokemon
import com.miguel.pokefan.utilidades.InstanciarRetrofit
import com.miguel.pokefan.utilidades.obtenerNumero
import kotlinx.coroutines.launch
import com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList as AdapterPokemonList1

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llenarRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun llenarRecyclerView(){
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        lifecycleScope.launch{
            val call = retrofit.getRetrofit(urlPokemon).create(APIServerPokeFan::class.java).getAllPokemon("20","20")
            val pokemon = call.body()
            var patrones: String
            var urlImgenPokemon:String
            if (call.isSuccessful){
                val pokes = pokemon?.results
                val items_pokemon: MutableList<pokemon> = ArrayList()
                for (i in pokes?.indices!!){
                    patrones = obtenerNumero(pokes[i].url)
                    urlImgenPokemon = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${patrones}.png"
                    items_pokemon.add(pokemon(
                        urlImgenPokemon,
                        pokes[i].name,
                        patrones
                    ))
                }
                val linearLayout = LinearLayoutManager(context)
                linearLayout.orientation = LinearLayoutManager.VERTICAL
                binding.recyclerviewPokemon.layoutManager = linearLayout
                binding.recyclerviewPokemon.hasFixedSize()
                val adapterPokemonList = AdapterPokemonList1(items_pokemon)
                binding.recyclerviewPokemon.adapter =  adapterPokemonList
            }
        }
    }
}