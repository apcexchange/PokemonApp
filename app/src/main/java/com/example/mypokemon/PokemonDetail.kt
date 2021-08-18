package com.example.mypokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypokemon.Adapters.PokemonEvolutionAdapter
import com.example.mypokemon.Adapters.PokemonTypeAdapter
import com.example.mypokemon.Common.Common
import com.example.mypokemon.Model.Evolution
import com.example.mypokemon.Model.Pokemon


private const val ARGS_PARAM2 = "param2"
class PokemonDetail : Fragment() {
    internal lateinit var pokemon_img: ImageView
    internal lateinit var pokemon_name: TextView
    internal lateinit var pokemon_height: TextView
    internal lateinit var pokemon_weight: TextView

    lateinit var recycler_type: RecyclerView
    lateinit var recycler_weakness: RecyclerView
    lateinit var recycler_prev_evolution: RecyclerView
    lateinit var recycler_next_evolution: RecyclerView

    companion object{
        internal var instance: PokemonDetail? = null

        fun getInstance(): PokemonDetail{
            if (instance == null)
                instance = PokemonDetail()
            return instance!!
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        val pokemon: Pokemon?
        if (arguments?.getString("num",)==null)
            pokemon = Common.pokemonList[requireArguments().getInt("position")]
        else
            pokemon = Common.findPokemonByNum(arguments?.getString("num"))

        pokemon_img = itemView.findViewById(R.id.pokemon_image)

        pokemon_name = itemView.findViewById(R.id.name)
        pokemon_height = itemView.findViewById(R.id.height)
        pokemon_weight = itemView.findViewById(R.id.weight)

        recycler_next_evolution = itemView.findViewById(R.id.recycler_next_evolution)
        recycler_next_evolution.setHasFixedSize(true)
        recycler_next_evolution.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_prev_evolution = itemView.findViewById(R.id.recycler_prev_evolution)
        recycler_prev_evolution.setHasFixedSize(true)
        recycler_prev_evolution.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_type = itemView.findViewById(R.id.recycler_type)
        recycler_type.setHasFixedSize(true)
        recycler_type.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_weakness = itemView.findViewById(R.id.recycler_weakness)
        recycler_weakness.setHasFixedSize(true)
        recycler_weakness.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        setDetailPokemon(pokemon)
        return itemView
    }

    private fun setDetailPokemon(pokemon: Pokemon?) {
//        load image
        if (pokemon != null) {
            Glide.with(this).load(pokemon.img).into(pokemon_img)
            pokemon_name.text = pokemon.name
            pokemon_height.text = "Height: " +pokemon.height
            pokemon_weight.text = "Weight: " +pokemon.weight


            val typeAdapter = PokemonTypeAdapter(requireContext(),pokemon.type!!)
            recycler_type.adapter = typeAdapter

            val weaknessAdapter = PokemonTypeAdapter(requireContext(),pokemon.weaknesses!!)
            recycler_weakness.adapter = weaknessAdapter

            if (pokemon.prev_evolution != null) {
                val prevEvolution =
                    PokemonEvolutionAdapter(requireContext(), pokemon.prev_evolution)
                recycler_prev_evolution.adapter = prevEvolution
            }

            if (pokemon.next_evolution != null) {
                val nextEvolution =
                    PokemonEvolutionAdapter(requireContext(), pokemon.next_evolution)
                recycler_next_evolution.adapter = nextEvolution
            }
        }
    }

}