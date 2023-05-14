package com.example.mad_04.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mad_04.models.HotelModel
import com.example.mad_04.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var Hname: EditText
    private lateinit var Haddress: EditText
    private lateinit var PackageDetails: EditText
    private lateinit var Price: EditText
    private lateinit var Phone: EditText
    private lateinit var Email: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        Hname = findViewById(R.id.Hname)
        Haddress = findViewById(R.id.Haddress)
        PackageDetails = findViewById(R.id.packageDetails)
        Price = findViewById(R.id.price)
        Phone = findViewById(R.id.Phone)
        Email = findViewById(R.id.Email)
        btnSaveData = findViewById(R.id.btnUpdateData)

        dbRef = FirebaseDatabase.getInstance().getReference("Restaurant")

        btnSaveData.setOnClickListener {
            saveRestaurantData()
        }
    }

    private fun saveRestaurantData() {
        val HName = Hname.text.toString()
        val HAddress = Haddress.text.toString()
        val packageDetails = PackageDetails.text.toString()
        val price = Price.text.toString()
        val phone = Phone.text.toString()
        val email = Email.text.toString()

        if (HName.isEmpty()) {
            Hname.error = "Please Enter Name"
        }
        if (HAddress.isEmpty()) {
            Haddress.error = "Please Enter Address"
        }
        if (packageDetails.isEmpty()) {
            PackageDetails.error = "Please Enter Package Details"
        }
        if (price.isEmpty()) {
            Price.error = "Please Enter Price"
        }
        if (phone.isEmpty()) {
            Phone.error = "Please Enter Phone Number"
        }
        if (email.isEmpty()) {
            Email.error = "Please Enter Email"
        }

        val hotelID = dbRef.push().key!!
        val hotel = HotelModel(hotelID, HName, HAddress, packageDetails,price, phone, email)

        dbRef.child(hotelID).setValue(hotel)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                Hname.text.clear()
                Haddress.text.clear()
                PackageDetails.text.clear()
                Price.text.clear()
                Phone.text.clear()
                Email.text.clear()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Error ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }
}
