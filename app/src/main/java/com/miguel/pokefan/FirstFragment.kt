package com.miguel.pokefan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.databinding.FragmentFirstBinding
import com.miguel.pokefan.reclyclerview.pokemon
import com.miguel.pokefan.utilidades.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList as AdapterPokemonList1

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    var rangoMayor: String? = null
    var rangoMenor: String? = null
    var pokemonStatusImage = PokemonStatusImage()
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
        lifecycleScope.launch { llenarRecyclerView("0") }
        binding.buttonAdelante.setOnClickListener {
            if(rangoMayor!=null){
                llenarRecyclerView(rangoMayor.toString())
            } else{
                Toast.makeText(context,"Ya no hay pokemon", Toast.LENGTH_LONG).show()
            }
        }
        binding.buttonAtras.setOnClickListener {
            if (rangoMenor!=null){
                llenarRecyclerView(rangoMenor.toString())
            } else{
                Toast.makeText(context,"Ya no hay pokemon", Toast.LENGTH_LONG).show()
            }
        }
        //implementacion de la busqueda del pokemon
        binding.txtBuscarPokemon.setOnEditorActionListener { v, actionId, event ->
            val busqueda = Busquedas()
            val txtBuscarPokemon = binding.txtBuscarPokemon
            val layoutBuscarPokemon = binding.layoutBuscarPokemon
            if (actionId == EditorInfo.IME_ACTION_DONE){
                val pokemon = txtBuscarPokemon.text
                layoutBuscarPokemon.error = null
                if (pokemon?.isEmpty() == false) {
                    layoutBuscarPokemon.error = null
                    //la data siempre debe ir en minusculas
                    lifecycleScope.launch {
                        busqueda.BuscarPokemon(pokemon.toString().lowercase(),txtBuscarPokemon,layoutBuscarPokemon)
                        val arrayDePokemon = busqueda.ArrayAdapterPokemon()
                        RecyclerView(arrayDePokemon)
                    }

                } else {
                    binding.layoutBuscarPokemon.error = null
                    binding.layoutBuscarPokemon.clearFocus()
                    llenarRecyclerView(rangoMenor.toString())
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun llenarRecyclerView(rango:String){
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        binding.shimmerViewContainer.startShimmer()
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.recyclerviewPokemon.visibility = View.GONE
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
                    val namePokemon = pokes[i].name
                    pokemonAlternative(pokes[i].name)
                    // Validando el valor de la variable urlPokoemon
                        urlImgenPokemon = if (pokemonStatusImage.getPokemonUrl() != "null"){
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${patrones}.png"
                        } else{
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${patrones}.png"
                        }
                    items_pokemon.add(pokemon(
                        urlImgenPokemon,
                        namePokemon,
                        patrones
                    ))
                }
                binding.shimmerViewContainer.visibility = View.GONE
                binding.recyclerviewPokemon.visibility = View.VISIBLE
                binding.shimmerViewContainer.stopShimmer()
                RecyclerView (items_pokemon)
               }
            }
    }

    private fun RecyclerView (items_pokemon: MutableList<pokemon>){
        val linearLayout = LinearLayoutManager(context)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerviewPokemon.layoutManager = linearLayout
        binding.recyclerviewPokemon.hasFixedSize()
        val adapterPokemonList = AdapterPokemonList1(items_pokemon)
        binding.recyclerviewPokemon.adapter =  adapterPokemonList
        adapterPokemonList.notifyDataSetChanged()
    }

    /**
     * @param namePokemon -> Nombre de pokemon
     * @return true -> si existe
     * -> false si no existe la imagen de pokemon Go
     * */
    private suspend fun pokemonAlternative(namePokemon: String) {
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        val call =  retrofit.getRetrofit(urlPokemon).create(APIServerPokeFan::class.java)
            .getPokemon(namePokemon)
        if (call.isSuccessful) {
            val pokemon = call.body()
            val urlimage = pokemon?.sprites?.other?.home
            pokemonStatusImage.setPokemonUrl(urlimage?.front_default.toString())
        }
    }

}