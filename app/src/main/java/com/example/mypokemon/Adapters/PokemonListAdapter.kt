package com.example.mypokemon.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypokemon.Common.Common
import com.example.mypokemon.Interface.ItemClickListener
import com.example.mypokemon.Model.Pokemon
import com.example.mypokemon.R

class PokemonListAdapter(var context: Context,
                         var pokemonList:List<Pokemon>):RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var img_pokemon: ImageView = itemView.findViewById(R.id.pokemon_image) as ImageView
        internal var txt_pokemon: TextView = itemView.findViewById(R.id.pokemon_name) as TextView

//        implementing clickListener
        init {
            itemView.setOnClickListener { view -> itemClickListener!!.onClick(view,adapterPosition) }
        }
        internal var itemClickListener : ItemClickListener? = null


        fun setItemClickListener(itemClickListener: ItemClickListener){
            this.itemClickListener = itemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView : View = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.img_pokemon)
        holder.txt_pokemon.text = pokemonList[position].name

        holder.setItemClickListener(object:ItemClickListener{
            override fun onClick(view: View, position: Int) {
//                Toast.makeText(context,"you clicked pokemon: " +pokemonList[position],Toast.LENGTH_LONG).show()

                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position",position))
            }

        })
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}