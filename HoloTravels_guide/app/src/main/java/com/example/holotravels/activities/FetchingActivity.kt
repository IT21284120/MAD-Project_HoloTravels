package com.example.holotravels.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holotravels.R
import com.example.holotravels.adapters.GuideAdapter
import com.example.holotravels.models.GuideModel
import com.google.firebase.database.*
import kotlin.jvm.internal.Ref

class FetchingActivity : AppCompatActivity() {

    private lateinit var guideRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var guidList: ArrayList<GuideModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        guideRecyclerView = findViewById(R.id.rvGuide)
        guideRecyclerView.layoutManager = LinearLayoutManager(this)
        guideRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        guidList = arrayListOf<GuideModel>()

        getGuideData()
    }

    private fun getGuideData(){
        guideRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Guides")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                guidList.clear()
                if (snapshot.exists()){
                    for (guideSnap in snapshot.children){
                        val guidData = guideSnap.getValue(GuideModel::class.java)
                        guidList.add(guidData!!)
                    }
                    val mAdapter = GuideAdapter(guidList)
                    guideRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : GuideAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                           val intent = Intent(this@FetchingActivity, GuideDetailsActivity::class.java)

                            //put extra data
                            intent.putExtra("guideId", guidList[position].guideId)
                            intent.putExtra("guideName", guidList[position].guideName)
                            intent.putExtra("guidePNum", guidList[position].guidePNum)
                            intent.putExtra("guideLan", guidList[position].guideLan)
                            intent.putExtra("guideHours", guidList[position].guideHours)
                            startActivity(intent)

                        }

                    })

                    guideRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}