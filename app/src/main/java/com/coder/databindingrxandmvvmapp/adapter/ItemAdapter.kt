package com.coder.databindingrxandmvvmapp.adapter

import com.coder.databindingrxandmvvmapp.classes.Item
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_layout.view.*
import androidx.recyclerview.widget.RecyclerView
import com.coder.databindingrxandmvvmapp.R

class ItemAdapter (private val itemList : ArrayList<Item>, private val listener : Listener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    interface Listener {
        fun onStoreItemClick(item: Item)
    }

    //Define an array of colours//
    private val colors : Array<String> = arrayOf("#7E57C2", "#42A5F5", "#26C6DA", "#66BB6A", "#FFEE58", "#FF7043" , "#EC407A" , "#d32f2f")

    //Bind the ViewHolder//
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Pass the position where each item should be displayed//
        holder.bind(itemList[position], listener, colors, position)
    }

    //Check how many items you have to display//
    override fun getItemCount(): Int = itemList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    //Create a ViewHolder class for your RecyclerView items//
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        //Assign values from the data model, to their corresponding Views//
        fun bind(item: Item, listener: Listener, colors : Array<String>, position: Int) {
            //Listen for user input events//
            Log.d("item", item.toString());
            itemView.setOnClickListener{ listener.onStoreItemClick(item) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.text_name.text = item.itemName
            itemView.text_price.text = item.itemCost
        }
    }
}