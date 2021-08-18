package com.example.mypokemon.Common

import android.graphics.Color
import com.example.mypokemon.Model.Pokemon

object Common {
    fun findPokemonByNum(num: String?): Pokemon? {
        for (pokemon:Pokemon in Common.pokemonList)
            if (pokemon.num.equals(num))
                return pokemon
        return null
    }

    fun getColorByType(type: String): Int {


        when(type) {
            "Normal" -> return Color.parseColor("#A4A27A");


            "Dragon" -> return Color.parseColor("#743BFB");



            "Psychic" -> return Color.parseColor("#F15B85");


            "Electric" -> return Color.parseColor("#E9CA3C");


            "Ground" -> return Color.parseColor("#D9BF6C");

            "Grass" -> return Color.parseColor("#81C85B");

            "Poison" -> return Color.parseColor("#A441A3");

            "Steel" -> return Color.parseColor("#BAB7D2");


            "Fairy" -> return Color.parseColor("#DDA2DF");


            "Fire" -> return Color.parseColor("#F48130");


            "Fight" -> return Color.parseColor("#BE3027");


            "Bug" -> return Color.parseColor("#A8B822");


            "Ghost" -> return Color.parseColor("#705693");

            "Dark" -> return Color.parseColor("#745945");


            "Ice" -> return Color.parseColor("#9BD8D8");


            "Water" -> return Color.parseColor("#658FF1");
            else -> return Color.parseColor("#658FA0");
        }
    }

//    public static Pokemon findPokemonByNum(String num) {
//        for(Pokemon pokemon : commonPokemonList)
//        {
//            if(pokemon.getNum().equals(num))
//                return pokemon;
//        }
//        return null;
//    }
//
//    public static List<Pokemon> findPokemonsByType(String type) {
//        List<Pokemon> result = new ArrayList<>()
//        for(Pokemon pokemon : commonPokemonList)
//        {
//            if(pokemon.getType().contains(type))
//                result.add(pokemon)
//        }
//        return result
//    }
//    }

    val KEY_NUM_EVLUTION = "evolution"
    var pokemonList: List<Pokemon> = ArrayList()
    val KEY_ENABLE_HOME = "position"
}