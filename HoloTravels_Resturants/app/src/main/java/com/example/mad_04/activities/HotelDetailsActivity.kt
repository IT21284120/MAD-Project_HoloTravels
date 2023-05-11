package com.example.mad_04.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_04.R

class HotelDetailsActivity: AppCompatActivity() {

    private lateinit var hotelId: TextView
    private lateinit var HName: TextView
    private lateinit var Haddress: TextView
    private lateinit var packageDetails: TextView
    private lateinit var price: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoteldetails)

        initView()
        setValuesToViews()
    }

    private fun initView() {
        hotelId = findViewById(R.id.TextView)
        HName = findViewById(R.id.TextView1)
        Haddress = findViewById(R.id.TextView2)
        packageDetails = findViewById(R.id.TextView3)
        price = findViewById(R.id.TextView4)
        phone = findViewById(R.id.TextView5)
        email = findViewById(R.id.TextView6)
    }

    private fun setValuesToViews() {
        hotelId.text = intent.getStringExtra("hotelId")
        HName.text = intent.getStringExtra("HName")
        Haddress.text = intent.getStringExtra("Haddress")
        packageDetails.text = intent.getStringExtra("packageDetails")
        price.text = intent.getStringExtra("price")
        phone.text = intent.getStringExtra("phone")
        email.text = intent.getStringExtra("email")
    }
}
