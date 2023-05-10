package com.example.holotravels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holotravels.R
import com.example.holotravels.models.GuideModel

class GuideAdapter (private val guideList: ArrayList<GuideModel>) :
    RecyclerView.Adapter<GuideAdapter.ViewHolder> () {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.guide_list_item, parent, false)
        return ViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGuide = guideList[position]
        holder.tvGuideName.text = currentGuide.guideName
    }

    override fun getItemCount(): Int {
        return guideList.size
    }

    class ViewHolder(itemView: View, clickListener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvGuideName : TextView = itemView.findViewById(R.id.tvGuideName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }



}