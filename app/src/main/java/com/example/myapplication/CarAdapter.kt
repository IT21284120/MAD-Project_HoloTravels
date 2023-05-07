package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter (private val carlist:ArrayList<CarModel>) : RecyclerView.Adapter<CarAdapter.ViewHolder>(){

    private lateinit var mListner: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListner = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view,parent, false)
        return ViewHolder(itemView,mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCar = carlist[position]
        holder.tvcarname.text = currentCar.cmodel
        holder.tvcarname1.text = currentCar.cprice
        holder.tvcarname2.text = currentCar.pphone

    }

    override fun getItemCount(): Int {
        return carlist.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvcarname : TextView = itemView.findViewById(R.id.tvCarName)
        val tvcarname1 : TextView = itemView.findViewById(R.id.tvCarName1)
        val tvcarname2 : TextView = itemView.findViewById(R.id.tvCarName2)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}
