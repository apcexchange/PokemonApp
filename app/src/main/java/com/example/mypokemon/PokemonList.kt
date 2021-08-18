package com.example.mypokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemon.Adapters.PokemonListAdapter
import com.example.mypokemon.Common.Common
import com.example.mypokemon.Common.ItemOffsetDecoration
import com.example.mypokemon.Retrofit.IPokemonList
import com.example.mypokemon.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PokemonList : Fragment() {
    internal lateinit var recyclerview: RecyclerView
    internal var compositeDisposable = CompositeDisposable()
    internal var iPokemonList:IPokemonList

    init {
        val retrofit = RetrofitClient.instance
        iPokemonList = retrofit.create<IPokemonList>(IPokemonList::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
//        itemView.findViewById<RecyclerView>(R.id.pokemon_recyclerview)
        recyclerview = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = GridLayoutManager(activity,2)

        val itemDecoration = ItemOffsetDecoration(requireContext(),R.dimen.spacing)
        recyclerview.addItemDecoration(itemDecoration)
        fetchData()
        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokemonDex ->
                Common.pokemonList = pokemonDex.pokemon!!
                val adapter = PokemonListAdapter(requireContext(),Common.pokemonList)
                recyclerview.adapter = adapter
            }
        )
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        pokemon_recyclerview = view.findViewById(R.id.pokemon_recyclerview)
//    }


}