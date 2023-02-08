package com.miguel.pokefan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.databinding.FragmentFirstBinding
import com.miguel.pokefan.reclyclerview.pokemon
import com.miguel.pokefan.utilidades.InstanciarRetrofit
import com.miguel.pokefan.utilidades.obtenerNumero
import com.miguel.pokefan.utilidades.obtenerRangoMayor
import com.miguel.pokefan.utilidades.obtenerRangoMenor
import kotlinx.coroutines.launch
import com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList as AdapterPokemonList1

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    var rangoMayor: String? = null
    var rangoMenor: String? = null
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
        llenarRecyclerView("0")
        binding.buttonAdelante.setOnClickListener {
            println("RANGOCLICK: "+rangoMayor)
            llenarRecyclerView(rangoMayor.toString())
        }
        binding.buttonAtras.setOnClickListener {
            if (rangoMenor!=null){
                llenarRecyclerView(rangoMenor.toString())
            } else{
                Toast.makeText(context,"Ya no hay pokemon", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun llenarRecyclerView(rango:String){
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        lifecycleScope.launch{
            val call = retrofit.getRetrofit(urlPokemon).create(APIServerPokeFan::class.java).getAllPokemon(rango,"20")
            val pokemon = call.body()
            var patrones: String
            var urlImgenPokemon:String
            rangoMayor = obtenerRangoMayor(pokemon?.next.toString())
            rangoMenor = obtenerRangoMenor(pokemon?.previous.toString())
            if (call.isSuccessful){
                val pokes = pokemon?.results
                val items_pokemon: MutableList<pokemon> = ArrayList()
                for (i in pokes?.indices!!){
                    patrones = obtenerNumero(pokes[i].url)
                    urlImgenPokemon = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${patrones}.png"
                    println(urlImgenPokemon)
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

    fun obtenerDataAlternativa(namePokemon: String){
        val retrofit = InstanciarRetrofit()
    }

}