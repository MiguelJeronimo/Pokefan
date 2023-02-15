package com.miguel.pokefan

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.miguel.pokefan.databinding.FragmentSecondBinding
import com.miguel.pokefan.utilidades.Busquedas
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    var id_pokemon: String = ""
    var busqueda = Busquedas()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            id_pokemon = arguments?.getString("ID_Pokemon").toString()
            lifecycleScope.launch {
                busqueda.Pokedex(
                pokemon = id_pokemon,
                binding = binding,
                context = requireContext()
            ) }
            binding.switchShanyMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    //modo shainy
                    val urlshainy = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/${id_pokemon}.png"
                    Glide.with(context)
                        .load(urlshainy)
                        .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        .into(binding.imageViewPokemon)
                } else{
                    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id_pokemon}.png"
                    Glide.with(context)
                        .load(url)
                        .error(com.google.android.material.R.drawable.mtrl_ic_error)
                        .into(binding.imageViewPokemon)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}