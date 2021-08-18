package com.example.mypokemon.Retrofit


import com.example.mypokemon.Model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {
    @get: GET("pokedex.json")
    val listPokemon: Observable<Pokedex>
}