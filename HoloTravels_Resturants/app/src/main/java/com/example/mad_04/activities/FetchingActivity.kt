package com.example.mad_04.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_04.R
import com.example.mad_04.adapters.HAdapter
import com.example.mad_04.models.HotelModel
import com.google.firebase.database.*


class FetchingActivity : AppCompatActivity() {

    private lateinit var hRecyclerView:RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var HList:ArrayList<HotelModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)


        hRecyclerView = findViewById(R.id.rvHotel)
        hRecyclerView.layoutManager = LinearLayoutManager(this)
        hRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        HList = arrayListOf<HotelModel>()

        getHotelData()
    }

    private fun getHotelData(){
        hRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE
         dbRef = FirebaseDatabase.getInstance().getReference("Restaurant")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                HList.clear()
                if(snapshot.exists()){
                    for (HSnap in snapshot.children){
                        val HData=HSnap.getValue(HotelModel::class.java)
                        HList.add(HData!!)
                    }
                    val mAdapter = HAdapter(HList)
                    hRecyclerView.adapter = mAdapter
                    mAdapter.setOnItemClickListener(object :HAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,HotelDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("hotelId",HList[position].hotelID)
                            intent.putExtra("HName",HList[position].HName)
                            intent.putExtra("price",HList[position].price)
                            intent.putExtra("phone",HList[position].phone)
                            startActivity(intent)

                        }

                    })

                    hRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility =View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}