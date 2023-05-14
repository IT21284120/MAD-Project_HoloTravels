package com.example.mad_04.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_04.R
import com.example.mad_04.models.HotelModel

class HAdapter (private val HList:ArrayList<HotelModel>):RecyclerView.Adapter<HAdapter.ViewHolder>(){
    private lateinit var mListener:onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hotel_list_items,parent,false )
        return ViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentH =HList[position]
        holder.tvHname.text = currentH.HName
        holder.tvhotelprice.text = currentH.price
        holder.tvHphone.text = currentH.phone

    }

    override fun getItemCount(): Int {
        return HList.size
    }


    class ViewHolder(itemView : View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){

        val tvHname:TextView =itemView.findViewById(R.id.tvName)
        val tvhotelprice:TextView =itemView.findViewById(R.id.TextView4)
        val tvHphone:TextView =itemView.findViewById(R.id.TextView5)
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick((adapterPosition))
            }
        }

    }
}
