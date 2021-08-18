package com.example.mypokemon.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokemon.Common.Common
import com.example.mypokemon.Interface.ItemClickListener
import com.example.mypokemon.R
import com.robertlevonyan.views.chip.Chip

class PokemonTypeAdapter (internal var context:Context, internal var typeList:List<String>):RecyclerView.Adapter<PokemonTypeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList[position]))
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

  inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var chip: Chip
//        internal var itemClickListener: ItemClickListener? = null

//        fun setItemClickListener(itemClickListener: ItemClickListener){
//            this.itemClickListener = itemClickListener
//        }

        init {
            chip = itemView.findViewById(R.id.chip)
            chip.setOnChipClickListener { Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show() }
        }
    }


}



