package com.example.holotravels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holotravels.R
import com.example.holotravels.models.UserModel

class UserAdapter (private val userList: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder> () {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.tvUserName.text = currentUser.userName
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }



}