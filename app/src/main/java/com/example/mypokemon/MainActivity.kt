package com.example.mypokemon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mypokemon.Common.Common
import com.example.mypokemon.Model.Pokemon

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    //    create broadcast handle
    private val showDetail = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action.toString() == Common.KEY_ENABLE_HOME) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

//            replace fragment
                val detailFragment = PokemonDetail.getInstance()
                val position = intent.getIntExtra("position", -1)
                val bundle = Bundle()
                bundle.putInt("position", position)
                detailFragment.arguments = bundle

                val fragmentTransaction: FragmentTransaction =
                    supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

//            set pokemon name for toolbar
                val pokemon: Pokemon = Common.pokemonList[position]
                toolbar.title = pokemon.name

            }

        }
    }
    private val showEvolution = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action.toString() == Common.KEY_NUM_EVLUTION) {

//            replace fragment
                val detailFragment: PokemonDetail = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num: String? = intent.getStringExtra("num")
                bundle.putString("num",num)
                detailFragment.arguments = bundle

                val fragmentTransaction: FragmentTransaction =
                    supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(detailFragment)
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

//            set pokemon name for toolbar
                val pokemon: Pokemon? = Common.findPokemonByNum(num)
                if (pokemon != null) {
                    toolbar.title = pokemon.name
                }

            }

        }
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            toolbar = findViewById(R.id.tool_bar)
            toolbar.setTitle("POKEMAN APP LIST")
            setSupportActionBar(toolbar)

//            Register broadcast

            LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

            LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVLUTION))
        }
//  handling backButton
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                toolbar.title = "POKEMON LIST"
//clear all fragment in the stack with the name detail
                supportFragmentManager.popBackStack("detail",FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setDisplayShowHomeEnabled(false)
            }
        }
        return true
    }
}