package com.miguel.pokefan

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguel.pokefan.APISERVER.APIServerPokeFan
import com.miguel.pokefan.databinding.FragmentFirstBinding
import com.miguel.pokefan.reclyclerview.pokemon
import com.miguel.pokefan.utilidades.*
import com.miguel.pokefan.viewmodels.ViewModelRecyclerView
import kotlinx.coroutines.*
import com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList as AdapterPokemonList1


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@Suppress("DEPRECATION")
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    var rangoMayor: String? = null
    var rangoMenor: String? = null
    var pokemonStatusImage = PokemonStatusImage()
    var posicion = 0
    var rank: Int = 0
    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerView:RecyclerView
    //val items_pokemon: MutableList<pokemon> = ArrayList()
    private val viewModel: ViewModelRecyclerView by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var adapterPokemonList: com.miguel.pokefan.reclyclerview.adapters.AdapterPokemonList? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llenarRecyclerView("${rank}")
        recyclerView = view.findViewById(R.id.recyclerviewPokemon)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        viewModel.layoutManagerState.let {
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }

        binding.buttonAdelante.setOnClickListener {
            if(rangoMayor!=null){
                //items_pokemon.clear()
                rank = rangoMayor!!.toInt()
                llenarRecyclerView(rangoMayor.toString())
            } else{
                Toast.makeText(context,"Ya no hay pokemon", Toast.LENGTH_LONG).show()
            }
        }
        binding.buttonAtras.setOnClickListener {
            if (rangoMenor!=null){
                //items_pokemon.clear()
                rank = rangoMenor!!.toInt()
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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.layoutManagerState = recyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        if (adapterPokemonList!=null){
            posicion = adapterPokemonList!!.getPosicion()
        }
        super.onPause()
        _binding = null
    }

    private fun llenarRecyclerView(rango:String){
        val retrofit = InstanciarRetrofit()
        val urlPokemon = "https://pokeapi.co/api/v2/"
        //lifecycleScope
        //CoroutineScope(Dispatchers.IO)
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
            val items_pokemon: MutableList<pokemon> = ArrayList()
                if (call.isSuccessful){
                    val pokes = pokemon?.results
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

    @SuppressLint("NotifyDataSetChanged")
    private fun RecyclerView (items_pokemons: MutableList<pokemon>){
        println("ENTRO AL RECYCLER")
       // layoutManager.orie = LinearLayoutManager.VERTICAL
        adapterPokemonList = AdapterPokemonList1(items_pokemons)
        adapterPokemonList!!.setPosicion(posicion)
        recyclerView.adapter =  adapterPokemonList
        recyclerView.hasFixedSize()
        adapterPokemonList!!.setOnclickListener { view: View ->
            val recyclerview = binding.recyclerviewPokemon.getChildAdapterPosition(view)
            val ID = items_pokemons[recyclerview].pokemonId
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            EnviarDatosFragment(ID)
        }
        adapterPokemonList!!.notifyDataSetChanged()
    }


    fun EnviarDatosFragment(ID: String){
        val secondFragment = SecondFragment()
        val data = Bundle().apply {
            putString("ID_Pokemon",ID)
        }
        secondFragment.arguments = data
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * @param namePokemon -> Nombre de pokemon
     * @return true -> si existe
     * -> false si no existe la imagen de pokemon Go
     * */
    private fun pokemonAlternative(namePokemon: String) {
        val retrofit = InstanciarRetrofit()
        CoroutineScope(Dispatchers.IO).launch {
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

}
