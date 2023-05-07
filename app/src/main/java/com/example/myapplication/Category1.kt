package com.example.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Category1 : AppCompatActivity() {

    private lateinit var btnviewcategory1: Button
    private lateinit var backbutton: Button

    private lateinit var RecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var carlist:ArrayList<CarModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category1)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnviewcategory1 = findViewById(R.id.button)
        backbutton = findViewById(R.id.button6)

        btnviewcategory1.setOnClickListener {
            val intent = Intent(this, Addnew::class.java)
            startActivity(intent)
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        RecyclerView = findViewById(R.id.rview)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        carlist = arrayListOf<CarModel>()
        getCarData()

    }
    private fun getCarData(){
        RecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

       val dbRef = FirebaseDatabase.getInstance().getReference("Cars")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                carlist.clear()
                if (snapshot.exists()){
                    for (carSnap in snapshot.children){
                        val cardata = carSnap.getValue(CarModel::class.java)
                        carlist.add(cardata!!)
                    }
                    val mAdapter = CarAdapter(carlist)
                    RecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CarAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Category1, CarDetails::class.java)

                            intent.putExtra("carId",carlist[position].carId)
                            intent.putExtra("carModel",carlist[position].cmodel)
                            intent.putExtra("carNum",carlist[position].vnumber)
                            intent.putExtra("carCap",carlist[position].seatc)
                            intent.putExtra("carPrice",carlist[position].cprice)
                            intent.putExtra("PName",carlist[position].pname)
                            intent.putExtra("PPhone",carlist[position].pphone)
                            intent.putExtra("PEmail",carlist[position].pemail)
                            startActivity(intent)
                        }

                    })

                    RecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}