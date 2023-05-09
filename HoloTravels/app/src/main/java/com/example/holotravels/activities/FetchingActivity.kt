package com.example.holotravels.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holotravels.R
import com.example.holotravels.adapters.UserAdapter
import com.example.holotravels.models.UserModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        userList = arrayListOf<UserModel>()

        getUserData()
    }

    private fun getUserData(){
        userRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object :UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                           val intent = Intent(this@FetchingActivity,UserDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("userId",userList[position].userId)
                            intent.putExtra("userName",userList[position].userName)
                            intent.putExtra("userPNum",userList[position].userPNum)
                            intent.putExtra("userEmail",userList[position].userEmail)
                            intent.putExtra("userCountry",userList[position].userCountry)
                            startActivity(intent)


                        }

                    })

                    userRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}